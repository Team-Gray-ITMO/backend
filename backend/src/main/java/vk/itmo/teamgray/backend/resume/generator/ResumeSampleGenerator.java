package vk.itmo.teamgray.backend.resume.generator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.services.CertificationService;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionDto;
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
import vk.itmo.teamgray.backend.resume.services.LinkService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;
import vk.itmo.teamgray.backend.skill.services.SkillService;

@AllArgsConstructor
@Component
public class ResumeSampleGenerator {
    private CertificationService certificationService;

    private EducationService educationService;

    private EducationInstitutionService educationInstitutionService;

    private LanguageService languageService;

    private LinkService linkService;

    private CompanyService companyService;

    private JobService jobService;

    private SkillService skillService;

    private ResumeService resumeService;

    private final Random random = new Random();

    public ResumeDto generateResume(long userId) {
        return generateResume(null, userId, "", false);
    }

    ResumeDto generateResume(Long templateId, long userId, String suffix, boolean persist) {
        var resume = resumeService.createResume(
            new ResumeCreateDto(userId, "Test Summary " + suffix, templateId),
            persist
        );

        var certification1 = certificationService.createCertification(
            new CertificationCreateDto(
                resume.getId(),
                "Test Name " + suffix,
                "Test Org " + suffix,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                "https://test" + suffix + ".com",
                null
            ),
            persist
        );

        var certification2 = certificationService.createCertification(
            new CertificationCreateDto(
                resume.getId(),
                "Language Test Name " + suffix,
                "Language Test Org " + suffix,
                Date.from(Instant.now().plus(1, ChronoUnit.DAYS)),
                Date.from(Instant.now().plus(1, ChronoUnit.DAYS)),
                "https://test" + suffix + ".com",
                LanguageProficiency.A1
            ),
            persist
        );

        if (!persist) {
            resume.setCertifications(List.of(certification1, certification2));
        }

        var skillz = Arrays.stream(SkillProficiency.values())
            .map(proficiency -> skillService.createSkill(
                    new SkillCreateDto(
                        resume.getId(),
                        "Skill " + proficiency + " " + suffix,
                        proficiency
                    ),
                    persist
                )
            )
            .toList();

        if (!persist) {
            resume.setSkills(skillz);
        }

        var educationInstitutions = Arrays.stream(EducationDegreeType.values()).collect(Collectors.toMap(
            Function.identity(),
            degreeType -> educationInstitutionService.createEducationInstitution(
                new EducationInstitutionCreateDto(
                    "TestName " + degreeType + suffix
                ),
                persist
            )
        ));

        var educations = educationInstitutions.entrySet()
            .stream()
            .map(entry -> {
                EducationDegreeType degreeType = entry.getKey();
                EducationInstitutionDto suffixnstitution = entry.getValue();

                return educationService.createEducation(
                    new EducationCreateDto(
                        resume.getId(),
                        suffixnstitution.getId(),
                        degreeType,
                        "TestDegree " + degreeType + " " + suffix,
                        "TestField " + degreeType + " " + suffix,
                        "TestSpec " + degreeType + " " + suffix,
                        Date.from(Instant.now().plus(degreeType.ordinal(), ChronoUnit.DAYS)),
                        Date.from(Instant.now().plus(degreeType.ordinal(), ChronoUnit.DAYS)),
                        "Grade " + degreeType + " " + suffix
                    ),
                    persist
                );
            })
            .toList();

        if (!persist) {
            resume.setEducations(educations);
        }

        var languages = Stream.of("RU", "EN", "DE")
            .map(lang -> {
                var proficiency = Arrays.stream(LanguageProficiency.values())
                    .skip(random.nextInt(LanguageProficiency.values().length))
                    .findFirst()
                    .orElseThrow();

                return languageService.createLanguage(
                    new LanguageCreateDto(
                        resume.getId(),
                        lang,
                        proficiency
                    ),
                    persist
                );
            })
            .toList();

        if (!persist) {
            resume.setLanguages(languages);
        }

        var links = Stream.of("github", "linkedin")
            .map(platform -> linkService.createLink(
                new LinkCreateDto(
                    resume.getId(),
                    platform + " " + suffix,
                    "https://" + platform + ".com"
                ),
                persist
            ))
            .toList();

        if (!persist) {
            resume.setLinks(links);
        }

        var companies = Stream.of("Google", "Microsoft")
            .map(company -> companyService.createCompany(
                    new CompanyCreateDto(
                        company
                    ),
                    persist
                )
            )
            .toList();


        var jobs = companies.stream()
            .map(company -> jobService.createJob(
                new JobCreateDto(
                    resume.getId(),
                    company.getId(),
                    "TestTitle " + suffix,
                    "TestLocation " + suffix,
                    Date.from(Instant.now()),
                    Date.from(Instant.now()),
                    "TestDescription " + suffix
                ),
                persist
            ))
            .toList();

        if (!persist) {
            resume.setJobs(jobs);
        }

        if (persist) {
            return resumeService.findById(resume.getId());
        } else {
            return resume;
        }
    }
}
