package vk.itmo.teamgray.backend.resume.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.resume.entities.Link}
 */
public record LinkUpdateDto(
        Long id,
        Long resumeId,
        String platformName,
        String profileUrl
) implements Serializable {
}