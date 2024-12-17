package vk.itmo.teamgray.backend.template.services;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.file.format.PngFormat;
import vk.itmo.teamgray.backend.resume.generator.ResumeSampleGenerator;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateImageDto;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;

@AllArgsConstructor
@Service
public class TemplateImageService {
    private final ResumeSampleGenerator resumeSampleGenerator;

    private final TemplateService templateService;

    private final TemplateMergeService templateMergeService;

    private final ResumeService resumeService;

    public List<TemplateImageDto> generateImagesForAllTemplates() {
        return templateService.findAll().stream()
            .map(this::getTemplateImageDto)
            .toList();
    }

    public TemplateImageDto generateImageForTemplate(Long templateId) {
        return getTemplateImageDto(templateService.findById(templateId));
    }

    private TemplateImageDto getTemplateImageDto(TemplateDto template) {
        var sampleResume = resumeSampleGenerator.generateResume();

        return new TemplateImageDto(
            template.getId(),
            template.getName(),
            new FileDto(
                "image" + PngFormat.EXTENSION,
                PngFormat.MIME_TYPE,
                templateMergeService.mergeTemplateToHtml(template, resumeService.getResumeJsonForMerge(sampleResume))
            )
        );
    }
}
