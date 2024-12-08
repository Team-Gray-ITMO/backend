package vk.itmo.teamgray.backend;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.mapper.ResumeMapper;
import vk.itmo.teamgray.backend.resume.services.LinkService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;
import vk.itmo.teamgray.backend.skill.services.SkillService;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.service.UserService;

@Component
public class ResumeTestGenerator {
    @Autowired
    private UserService userService;

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
    private SkillService skillService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeMapper resumeMapper;

    private final Random random = new Random();

    public List<ResumeDto> generateResumes(int amount, long templateId) {
        var resumes = IntStream.range(1, amount + 1)
            .mapToObj(i -> {
                var user = userService.createUser(new UserCreateDto("email" + i + "@example.com", (long)i));

                var resume = resumeService.createResume(
                        new ResumeCreateDto(
                                user.getId(), "Test Summary " + i, templateId
                        )
                );

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
                        Date.from(Instant.now().plus(1, ChronoUnit.DAYS)),
                        Date.from(Instant.now().plus(1, ChronoUnit.DAYS)),
                        "https://test" + i + ".com",
                        LanguageProficiency.A1
                    )
                );

                Arrays.stream(SkillProficiency.values())
                    .forEach(proficiency -> skillService.createSkill(
                            new SkillCreateDto(
                                resume.getId(),
                                "Skill " + proficiency + " " + i,
                                proficiency
                            )
                        )
                    );

                var educationInstitutions = Arrays.stream(EducationDegreeType.values()).collect(Collectors.toMap(
                    Function.identity(),
                    degreeType -> educationInstitutionService.createEducationInstitution(
                        new EducationInstitutionCreateDto(
                            "TestName " + degreeType + i
                        )
                    )
                ));

                educationInstitutions
                    .forEach((degreeType, institution) -> educationService.createEducation(
                        new EducationCreateDto(
                            resume.getId(),
                            institution.getId(),
                            degreeType,
                            "TestDegree " + degreeType + " " + i,
                            "TestField " + degreeType + " " + i,
                            "TestSpec " + degreeType + " " + i,
                            Date.from(Instant.now().plus(degreeType.ordinal(), ChronoUnit.DAYS)),
                            Date.from(Instant.now().plus(degreeType.ordinal(), ChronoUnit.DAYS)),
                            "Grade " + degreeType + " " + i
                        )
                    ));


                Stream.of("RU", "EN", "DE").forEach(lang -> {
                    var proficiency = Arrays.stream(LanguageProficiency.values())
                        .skip(random.nextInt(LanguageProficiency.values().length))
                        .findFirst()
                        .orElseThrow();

                    languageService.createLanguage(
                        new LanguageCreateDto(
                            resume.getId(),
                            lang,
                            proficiency
                        )
                    );
                });

                Stream.of("github", "linkedin").forEach(platform -> linkService.createLink(
                    new LinkCreateDto(
                        resume.getId(),
                        platform + " " + i,
                        "https://" + platform + ".com"
                    )
                ));


                var companies = Stream.of("Google", "Microsoft")
                    .map(company -> companyService.createCompany(
                            new CompanyCreateDto(
                                company
                            )
                        )
                    )
                    .toList();


                companies.forEach(company -> jobService.createJob(
                    new JobCreateDto(
                        resume.getId(),
                        company.getId(),
                        "TestTitle " + i,
                        "TestLocation " + i,
                        Date.from(Instant.now()),
                        Date.from(Instant.now()),
                        "TestDescription " + i
                    )
                ));

                return resumeService.getById(resume.getId());
            })
            .toList();

        return resumes;
    }
}
