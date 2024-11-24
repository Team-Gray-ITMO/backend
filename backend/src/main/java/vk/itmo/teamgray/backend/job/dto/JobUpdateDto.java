package vk.itmo.teamgray.backend.job.dto;

import java.io.Serializable;
import java.util.Date;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

/**
 * DTO for {@link vk.itmo.teamgray.backend.job.entities.Job}
 */
public record JobUpdateDto(
        Long id,
        Long resumeId,
        Long companyId,
        String title,
        String location,
        Date startDate,
        Date endDate,
        String description
) implements Serializable {
}
