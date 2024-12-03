package vk.itmo.teamgray.backend.template.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link vk.itmo.teamgray.backend.template.entities.Template}
 */
public record TemplateBaseDto(long id, Instant createdAt, String name, String filePath) implements Serializable {
}