package vk.itmo.teamgray.backend.resume.generator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.cetification.mapper.CertificationMapper;
import vk.itmo.teamgray.backend.cetification.services.CertificationService;
import vk.itmo.teamgray.backend.company.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.company.dto.CompanyDto;
import vk.itmo.teamgray.backend.company.mapper.CompanyMapper;
import vk.itmo.teamgray.backend.company.service.CompanyService;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.mapper.EducationMapper;
import vk.itmo.teamgray.backend.education.services.EducationService;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.educationinstitution.mapper.EducationInstitutionMapper;
import vk.itmo.teamgray.backend.educationinstitution.services.EducationInstitutionService;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.mapper.JobMapper;
import vk.itmo.teamgray.backend.job.service.JobService;
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;
import vk.itmo.teamgray.backend.language.mapper.LanguageMapper;
import vk.itmo.teamgray.backend.language.services.LanguageService;
import vk.itmo.teamgray.backend.link.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.link.dto.LinkDto;
import vk.itmo.teamgray.backend.link.mapper.LinkMapper;
import vk.itmo.teamgray.backend.link.services.LinkService;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.mapper.ResumeMapper;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;
import vk.itmo.teamgray.backend.skill.mapper.SkillMapper;
import vk.itmo.teamgray.backend.skill.services.SkillService;
import vk.itmo.teamgray.backend.user.service.UserService;

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

    private UserService userService;

    private CertificationMapper certificationMapper;

    private EducationMapper educationMapper;

    private EducationInstitutionMapper educationInstitutionMapper;

    private LanguageMapper languageMapper;

    private LinkMapper linkMapper;

    private CompanyMapper companyMapper;

    private JobMapper jobMapper;

    private SkillMapper skillMapper;

    private ResumeMapper resumeMapper;

    private final Random random = new Random();

    public ResumeDto generateResume() {
        return generateResume(null, "", false);
    }

    ResumeDto generateResume(Long templateId, String suffix, boolean persist) {
        var resume = createResume(new ResumeCreateDto("Test Summary " + suffix), persist);

        var updateDto = new ResumeUpdateDto();

        updateDto.setId(resume.getId());
        updateDto.setTemplateId(templateId);

        if (persist) {
            resumeService.updateResume(updateDto);
        }

        var certification1 = createCertification(
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

        var certification2 = createCertification(
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
            resume.setCertifications(Stream.of(certification1, certification2).collect(Collectors.toCollection(ArrayList::new)));
        }

        var skillz = Arrays.stream(SkillProficiency.values())
            .map(proficiency -> createSkill(
                    new SkillCreateDto(
                        resume.getId(),
                        "Skill " + proficiency + " " + suffix,
                        proficiency
                    ),
                    persist
                )
            )
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setSkills(skillz);
        }

        var educationInstitutions = Arrays.stream(EducationDegreeType.values()).collect(Collectors.toMap(
            Function.identity(),
            degreeType -> createEducationInstitution(
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

                return createEducation(
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
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setEducations(educations);
        }

        var languages = Stream.of("RU", "EN", "DE")
            .map(lang -> {
                var proficiency = Arrays.stream(LanguageProficiency.values())
                    .skip(random.nextInt(LanguageProficiency.values().length))
                    .findFirst()
                    .orElseThrow();

                return createLanguage(
                    new LanguageCreateDto(
                        resume.getId(),
                        lang,
                        proficiency
                    ),
                    persist
                );
            })
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setLanguages(languages);
        }

        var links = Stream.of("github", "linkedin")
            .map(platform -> createLink(
                new LinkCreateDto(
                    resume.getId(),
                    platform + " " + suffix,
                    "https://" + platform + ".com"
                ),
                persist
            ))
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setLinks(links);
        }

        var companies = Stream.of("Google", "Microsoft")
            .map(company -> createCompany(
                    new CompanyCreateDto(
                        company
                    ),
                    persist
                )
            )
            .collect(Collectors.toCollection(ArrayList::new));


        var jobs = companies.stream()
            .map(company -> createJob(
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
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setJobs(jobs);
        }

        if (persist) {
            return resumeService.findById(resume.getId());
        } else {
            return resume;
        }
    }

    private CertificationDto createCertification(CertificationCreateDto certificationCreateDto, boolean persist) {
        if (persist) {
            return certificationService.createCertification(certificationCreateDto);
        } else {
            return certificationMapper.dryRun(certificationCreateDto);
        }
    }

    private EducationDto createEducation(EducationCreateDto educationCreateDto, boolean persist) {
        if (persist) {
            return educationService.createEducation(educationCreateDto);
        } else {
            return educationMapper.dryRun(educationCreateDto);
        }
    }

    private EducationInstitutionDto createEducationInstitution(EducationInstitutionCreateDto educationInstitutionCreateDto, boolean persist) {
        if (persist) {
            return educationInstitutionService.createEducationInstitution(educationInstitutionCreateDto);
        } else {
            return educationInstitutionMapper.dryRun(educationInstitutionCreateDto);
        }
    }

    private LanguageDto createLanguage(LanguageCreateDto languageCreateDto, boolean persist) {
        if (persist) {
            return languageService.createLanguage(languageCreateDto);
        } else {
            return languageMapper.dryRun(languageCreateDto);
        }
    }

    private LinkDto createLink(LinkCreateDto linkCreateDto, boolean persist) {
        if (persist) {
            return linkService.createLink(linkCreateDto);
        } else {
            return linkMapper.dryRun(linkCreateDto);
        }
    }

    private CompanyDto createCompany(CompanyCreateDto companyCreateDto, boolean persist) {
        if (persist) {
            return companyService.createCompany(companyCreateDto);
        } else {
            return companyMapper.dryRun(companyCreateDto);
        }
    }

    private JobDto createJob(JobCreateDto jobCreateDto, boolean persist) {
        if (persist) {
            return jobService.createJob(jobCreateDto);
        } else {
            return jobMapper.dryRun(jobCreateDto);
        }
    }

    private SkillDto createSkill(SkillCreateDto skillCreateDto, boolean persist) {
        if (persist) {
            return skillService.createSkill(skillCreateDto);
        } else {
            return skillMapper.dryRun(skillCreateDto);
        }
    }

    private ResumeDto createResume(ResumeCreateDto resumeCreateDto, boolean persist) {
        if (persist) {
            return resumeService.createResume(resumeCreateDto);
        } else {
            return resumeMapper.dryRun(resumeCreateDto);
        }
    }
}
