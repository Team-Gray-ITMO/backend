package vk.itmo.teamgray.backend.resume.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.resume.entities.Resume}
 */
public record ResumeUpdateDto(long id, String summary, long templateId) implements Serializable {
}