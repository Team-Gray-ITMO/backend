package vk.itmo.teamgray.backend;

import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vk.itmo.teamgray.backend.company.repos.CompanyRepository;
import vk.itmo.teamgray.backend.education.institution.repos.EducationInstitutionRepository;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.resume.generator.ResumeTestGenerator;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.services.TemplateService;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

import static vk.itmo.teamgray.backend.file.utils.ZipUtils.repackZip;
import static vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService.INDEX_HTML_FILENAME;

@SpringBootTest
public abstract class TestBase {
    @Autowired
    protected ResumeRepository resumeRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected EducationInstitutionRepository educationInstitutionRepository;

    @Autowired
    protected TemplateService templateService;

    @Autowired
    protected ResumeTestGenerator resumeGenerator;

    protected TemplateDto sampleTemplate;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        resumeRepository.deleteAll();
        companyRepository.deleteAll();
        educationInstitutionRepository.deleteAll();

        sampleTemplate = createSampleTemplate();
    }

    protected TemplateDto createSampleTemplate() {
        var fileDto = new FileDto();

        var filename = "sample_template";

        var filepath = "/templates/" + filename + ".vm";

        var zipFileName = filename + ".zip";

        fileDto.setFilename(zipFileName);
        fileDto.setContentType("application/zip");

        try (var stream = TestBase.class.getResourceAsStream(filepath)) {
            if (stream == null) {
                throw new AssertionError("Null stream for file " + filepath);
            }

            var zipFile = repackZip(Map.of(INDEX_HTML_FILENAME, stream.readAllBytes()));

            fileDto.setContent(zipFile);
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        var name = "Test_Template";

        return templateService.createTemplate(
            new TemplateCreateDto(
                name,
                fileDto
            )
        );
    }
}
