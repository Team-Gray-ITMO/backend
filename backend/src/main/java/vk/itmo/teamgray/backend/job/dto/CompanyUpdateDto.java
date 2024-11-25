package vk.itmo.teamgray.backend.job.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.job.entities.Company}
 */
public record CompanyUpdateDto(
        Long id,
        String name
) implements Serializable {
}
