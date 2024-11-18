package vk.itmo.teamgray.backend.cetification.dto;

import vk.itmo.teamgray.backend.language.dto.LanguageProficiency;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link vk.itmo.teamgray.backend.cetification.entities.Certification}
 */
public record CertificationUpdateDto(
        long id,
        Long resumeId,
        String name,
        String issuingOrganization,
        Date issueDate,
        Date expirationDate,
        String credentialUrl,
        LanguageProficiency languageProficiency
) implements Serializable {
}