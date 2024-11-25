package vk.itmo.teamgray.backend.job.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link vk.itmo.teamgray.backend.job.entities.Job}
 */
public record JobCreateDto(
    Long resumeId,
    Long companyId,
    String title,
    String location,
    Date startDate,
    Date endDate,
    String description
) implements Serializable {
}
