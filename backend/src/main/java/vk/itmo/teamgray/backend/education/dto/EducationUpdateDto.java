package vk.itmo.teamgray.backend.education.dto;

import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link vk.itmo.teamgray.backend.education.entities.Education}
 */
public record EducationUpdateDto(
        Long id,
        Long resumeId,
        Long educationInstitutionId,
        EducationDegreeType degreeType,
        String degreeName,
        String fieldOfStudy,
        String specialization,
        Date startDate,
        Date endDate,
        String grade
) implements Serializable {
}