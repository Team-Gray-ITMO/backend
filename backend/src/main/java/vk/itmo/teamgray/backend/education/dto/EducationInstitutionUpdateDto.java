package vk.itmo.teamgray.backend.education.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.education.entities.EducationInstitution}
 */
public record EducationInstitutionUpdateDto(
        Long id,
        String name
) implements Serializable {
}