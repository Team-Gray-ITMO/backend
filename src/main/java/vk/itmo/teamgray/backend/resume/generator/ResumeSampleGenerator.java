package vk.itmo.teamgray.backend.resume.generator;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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
import vk.itmo.teamgray.backend.education.enums.EducationAttendanceFormat;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.enums.EducationFormat;
import vk.itmo.teamgray.backend.education.mapper.EducationMapper;
import vk.itmo.teamgray.backend.education.services.EducationService;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.educationinstitution.mapper.EducationInstitutionMapper;
import vk.itmo.teamgray.backend.educationinstitution.services.EducationInstitutionService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.file.format.PngFormat;
import vk.itmo.teamgray.backend.file.utils.FileUtils;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;
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
        var resume = createResume(new ResumeCreateDto("Инженер-программист с 9000+ лет опыта. " + suffix), persist);

        var updateDto = new ResumeUpdateDto();

        byte[] testAvatarData;

        try {
            testAvatarData = FileUtils.getLocalResource("/assets/sample_data/spotty_vintage" + PngFormat.EXTENSION).getContentAsByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var image = new FileDto(
            "spotty" + PngFormat.EXTENSION,
            PngFormat.MIME_TYPE,
            testAvatarData
        );

        updateDto.setImage(image);
        updateDto.setId(resume.getId());
        updateDto.setTemplateId(templateId);
        updateDto.setPreferredSpecialities(List.of("Инженер-Программист", "Специалист по ковырянию в носу"));
        updateDto.setPreferredAttendanceFormats(Arrays.asList(JobAttendanceFormat.values()));
        updateDto.setReadyForBusinessTrips(true);
        updateDto.setReadyForRelocation(true);

        if (persist) {
            resumeService.updateResume(updateDto);
        }

        var certification1 = createCertification(
            new CertificationCreateDto(
                resume.getId(),
                "Oracle Certified Developer " + suffix,
                "Oracle " + suffix,
                Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration())),
                Date.from(Instant.now().plus(ChronoUnit.YEARS.getDuration())),
                "https://oracle" + suffix + ".com",
                null
            ),
            persist
        );

        var certification2 = createCertification(
            new CertificationCreateDto(
                resume.getId(),
                "IELTS " + suffix,
                "IELTS Org " + suffix,
                Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration())),
                Date.from(Instant.now().plus(ChronoUnit.YEARS.getDuration())),
                "https://ielts" + suffix + ".com",
                randomEnumValue(LanguageProficiency.values())
            ),
            persist
        );

        if (!persist) {
            resume.setCertifications(Stream.of(certification1, certification2).collect(Collectors.toCollection(ArrayList::new)));
        }

        var skillz = Stream.of("Java", "Kotlin", "JPA", "Hibernate", "Spring Framework")
            .map(skill -> createSkill(
                    new SkillCreateDto(
                        resume.getId(),
                        skill + " " + suffix,
                        randomEnumValue(SkillProficiency.values())
                    ),
                    persist
                )
            )
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setSkills(skillz);
        }

        var educationInstitutions = Arrays.stream(EducationDegreeType.values())
            .collect(Collectors.toMap(
                Function.identity(),
                degreeType -> createEducationInstitution(
                    new EducationInstitutionCreateDto(
                        "ФГБОУ " + degreeType.getTranslatedName() + " " + suffix
                    ),
                    persist
                )
            ));

        var i = new AtomicInteger(0);

        var educations = educationInstitutions.entrySet()
            .stream()
            .sorted(Comparator.comparingInt(i2 -> i2.getKey().ordinal()))
            .map(entry -> {
                EducationDegreeType degreeType = entry.getKey();
                EducationInstitutionDto suffixInstitution = entry.getValue();
                String translatedName = degreeType.getTranslatedName();

                boolean first = i.get() == 0;

                var endDate = Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration().multipliedBy(i.getAndIncrement())));
                var startDate = Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration().multipliedBy(i.getAndIncrement())));

                return createEducation(
                    new EducationCreateDto(
                        resume.getId(),
                        suffixInstitution.getId(),
                        "Подразделение " + translatedName + " " + suffix,
                        randomEnumValue(EducationFormat.values()),
                        randomEnumValue(EducationAttendanceFormat.values()),
                        degreeType,
                        "Степень " + translatedName + " " + suffix,
                        "Направление " + translatedName + " " + suffix,
                        "Профиль " + translatedName + " " + suffix,
                        startDate,
                        first ? null : endDate,
                        String.valueOf(random.nextInt(3) + 2)
                    ),
                    persist
                );
            })
            .collect(Collectors.toCollection(ArrayList::new));

        if (!persist) {
            resume.setEducations(educations);
        }

        var languages = Stream.of("Russian", "English", "German")
            .map(lang -> {
                var proficiency = randomEnumValue(LanguageProficiency.values());

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

        var links = Stream.of("GitHub", "LinkedIn")
            .map(platform -> createLink(
                new LinkCreateDto(
                    resume.getId(),
                    platform + " " + suffix,
                    "https://" + platform.toLowerCase(Locale.ROOT) + ".com"
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
                        company,
                        "https://" + company.toLowerCase(Locale.ROOT) + ".com"
                    ),
                    persist
                )
            )
            .collect(Collectors.toCollection(ArrayList::new));

        i.set(0);

        var jobs = companies.stream()
            .map(company -> {
                boolean first = i.get() == 0;

                var endDate = Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration().multipliedBy(i.getAndIncrement())));
                var startDate = Date.from(Instant.now().minus(ChronoUnit.YEARS.getDuration().multipliedBy(i.getAndIncrement())));

                return createJob(
                    new JobCreateDto(
                        resume.getId(),
                        company.getId(),
                        "Инженер " + suffix,
                        "Россия, г. Санкт-Петербург " + suffix,
                        startDate,
                        first ? null : endDate,
                        "Работал в поле " + suffix,
                        randomEnumValue(JobAttendanceFormat.values())
                    ),
                    persist
                );
            })
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

    private <T> T randomEnumValue(T[] enumArray) {
        return Arrays.stream(enumArray)
            .skip(random.nextInt(enumArray.length))
            .findFirst()
            .orElseThrow();
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
