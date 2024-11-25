package vk.itmo.teamgray.backend.resume.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.resume.entities.Resume}
 */
public record ResumeCreateDto(long userId, String summary) implements Serializable {
}
