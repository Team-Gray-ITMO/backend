package vk.itmo.teamgray.backend.template.merge.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.generator.ResumeSampleGenerator;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.exception.TemplateMergeServiceException;
import vk.itmo.teamgray.backend.template.services.TemplateService;
import vk.itmo.teamgray.backend.template.utils.TemplateUtils;
import vk.itmo.teamgray.backend.user.service.UserService;

import static vk.itmo.teamgray.backend.file.utils.ZipUtils.extractZipContents;
import static vk.itmo.teamgray.backend.file.utils.ZipUtils.repackZip;

@Service
@RequiredArgsConstructor
public class TemplateMergeService {
    public static final String INDEX_HTML_FILENAME = "index.html";

    private final TemplateService templateService;

    private final ResumeService resumeService;
    private final UserService userService;

    private final ResumeSampleGenerator resumeSampleGenerator;

    public List<TemplateDto> getAllTemplatesAndFill() {
        var sampleResume = resumeSampleGenerator.generateResume(userService.getAuthUser().getId());

        return templateService.findAll().stream()
            .peek(template -> template.setFile(mergeTemplate(template, resumeService.getResumeJsonForMerge(sampleResume))))
            .toList();
    }

    public FileDto mergeTemplate(long resumeId, long templateId) {
        var template = templateService.findById(templateId);

        var resume = resumeService.getResumeJsonForMerge(resumeService.findById(resumeId));

        return mergeTemplate(template, resume);
    }

    private FileDto mergeTemplate(TemplateDto templateDto, Map<String, Object> valuesMap) {
        Map<String, byte[]> zipContents = extractZipContents(templateDto.getFile());

        String templateContent = getTemplateContent(zipContents);

        byte[] processedHtmlBytes = processHtml(valuesMap, templateContent);

        zipContents.put(INDEX_HTML_FILENAME, processedHtmlBytes);

        byte[] newZipContent = repackZip(zipContents);

        return new FileDto(templateDto.getFile().getFilename(), templateDto.getFile().getContentType(), newZipContent);
    }

    public byte[] mergeTemplateToHtml(ResumeDto resume) {
        var template = templateService.findById(resume.getTemplate().getId());

        var resumeMap = resumeService.getResumeJsonForMerge(resume);

        Map<String, byte[]> zipContents = extractZipContents(template.getFile());

        String templateContent = getTemplateContent(zipContents);

        return processHtml(resumeMap, templateContent);
    }

    private static String getTemplateContent(Map<String, byte[]> zipContents) {
        byte[] templateBytes = zipContents.get(INDEX_HTML_FILENAME);

        if (templateBytes == null) {
            throw new IllegalArgumentException(INDEX_HTML_FILENAME + " not found in the provided zip file.");
        }

        return new String(templateBytes, StandardCharsets.UTF_8);
    }

    private static byte[] processHtml(Map<String, Object> valuesMap, String templateContent) {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "string");
        Velocity.addProperty("resource.loader.string.class", StringResourceLoader.class.getName());
        Velocity.init();

        StringResourceRepository repo = StringResourceLoader.getRepository();
        repo.putStringResource(INDEX_HTML_FILENAME, templateContent);

        VelocityContext context = new VelocityContext();
        context.put("utils", new TemplateUtils());

        valuesMap.forEach(context::put);

        try (
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
        ) {
            Velocity.mergeTemplate(INDEX_HTML_FILENAME, "UTF-8", context, writer);

            writer.flush();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new TemplateMergeServiceException("Failed to merge template content.", e);
        }
    }
}
