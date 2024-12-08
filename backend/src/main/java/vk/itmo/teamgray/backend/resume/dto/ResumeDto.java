package vk.itmo.teamgray.backend.resume.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;

@Data
public class ResumeDto {
    private long id;
    private Date createdAt;
    private String summary;
    private UserDto user;
    private List<LinkDto> links;
    private List<SkillDto> skills;
    private List<JobDto> jobs;
    private List<EducationDto> educations;
    private List<LanguageDto> languages;
    private List<CertificationDto> certifications;
    private TemplateDto template;
}

