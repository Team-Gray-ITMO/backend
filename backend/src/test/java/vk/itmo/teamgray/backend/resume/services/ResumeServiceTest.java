package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.services.CertificationService;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.services.EducationInstitutionService;
import vk.itmo.teamgray.backend.education.services.EducationService;
import vk.itmo.teamgray.backend.job.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.service.CompanyService;
import vk.itmo.teamgray.backend.job.service.JobService;
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;
import vk.itmo.teamgray.backend.language.services.LanguageService;
import vk.itmo.teamgray.backend.resume.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.service.UserService;

class ResumeServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeServiceTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private EducationInstitutionService educationInstitutionService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    void testJsonAggregation() throws JsonProcessingException {
        var resumeIds = IntStream.range(0, 1)
            .mapToLong(i -> {
                var user = userService.createUser(new UserCreateDto("email" + i + "@example.com", (long)i));

                var resume = resumeService.createResume(new ResumeCreateDto(user.getId(), "Test Summary " + i));

                certificationService.createCertification(
                    new CertificationCreateDto(
                        resume.getId(),
                        "Test Name " + i,
                        "Test Org " + i,
                        Date.from(Instant.now()),
                        Date.from(Instant.now()),
                        "https://test" + i + ".com",
                        null
                    )
                );

                certificationService.createCertification(
                    new CertificationCreateDto(
                        resume.getId(),
                        "Language Test Name " + i,
                        "Language Test Org " + i,
                        Date.from(Instant.now()),
                        Date.from(Instant.now()),
                        "https://test" + i + ".com",
                        LanguageProficiency.A1
                    )
                );

                var educationInstitution = educationInstitutionService.createEducationInstitution(
                    new EducationInstitutionCreateDto(
                        "TestName " + i
                    )
                );

                educationService.createEducation(
                    new EducationCreateDto(
                        resume.getId(),
                        educationInstitution.getId(),
                        EducationDegreeType.ELEMENTARY_SCHOOL,
                        "TestDegree " + i,
                        "TestField " + i,
                        "TestSpec " + i,
                        Date.from(Instant.now()),
                        Date.from(Instant.now()),
                        "Grade " + i
                    )
                );

                languageService.createLanguage(
                    new LanguageCreateDto(
                        resume.getId(),
                        "TestName " + i,
                        LanguageProficiency.A1
                    )
                );

                linkService.createLink(
                    new LinkCreateDto(
                        resume.getId(),
                        "TestName " + i,
                        "https://paltform" + i + ".url"
                    )
                );

                var company = companyService.createCompany(
                    new CompanyCreateDto(
                        "TestCompany " + i
                    )
                );

                var job = jobService.createJob(
                    new JobCreateDto(
                        resume.getId(),
                        company.getId(),
                        "TestTitle " + i,
                        "TestLocation " + i,
                        Date.from(Instant.now()),
                        Date.from(Instant.now()),
                        "TestDescription " + i
                    )
                );

                return resume.getId();
            })
            .boxed()
            .toList();

        var maps = resumeIds.stream()
            .map(id -> resumeService.getResumeJson(id))
            .toList();

        for (Map<String, Object> map : maps) {
            log.info(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
        }
    }
}
