package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import lombok.Data;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;

@Data
@Schema(description = "Resume Model")
public class ResumeDto {

    @Schema(description = "Unique identifier for the resume")
    private long id;

    @Schema(description = "Date the resume was created")
    private Date createdAt;

    @Schema(description = "Summary of the resume")
    private String summary;

    @Schema(description = "User information associated with the resume")
    private UserDto user;

    @Schema(description = "List of links associated with the resume")
    private List<LinkDto> links;

    @Schema(description = "List of skills included in the resume")
    private List<SkillDto> skills;

    @Schema(description = "List of job experiences included in the resume")
    private List<JobDto> jobs;

    @Schema(description = "List of educational qualifications included in the resume")
    private List<EducationDto> educations;

    @Schema(description = "List of languages spoken as part of the resume")
    private List<LanguageDto> languages;

    @Schema(description = "List of certifications included in the resume")
    private List<CertificationDto> certifications;

    @Schema(description = "Template used for the resume")
    private TemplateBaseDto template;
}
