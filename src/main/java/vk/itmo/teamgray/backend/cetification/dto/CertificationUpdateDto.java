package vk.itmo.teamgray.backend.cetification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@Schema(description = "Certification Update Model")
@Data
public class CertificationUpdateDto {
    @Positive
    @Schema(description = "Certification ID")
    private long id;

    @Schema(description = "Resume ID")
    private Long resumeId;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Certification name")
    private String name;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Issuer of the certification")
    private String issuingOrganization;

    @Past
    @Schema(description = "Issue date of the certification")
    private Date issueDate;

    @Schema(description = "Expiration date of the certification")
    private Date expirationDate;

    @URL
    @Size(max = 255)
    @Schema(description = "URL providing details about the credential")
    private String credentialUrl;

    @Schema(description = "Proficiency level in the associated language")
    private LanguageProficiency languageProficiency;
}
