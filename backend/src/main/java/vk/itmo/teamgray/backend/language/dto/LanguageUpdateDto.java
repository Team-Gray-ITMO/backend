package vk.itmo.teamgray.backend.language.dto;

import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.language.entities.Language}
 */
public record LanguageUpdateDto(
        Long id,
        Long resumeId,
        String name,
        LanguageProficiency proficiency
) implements Serializable {
}