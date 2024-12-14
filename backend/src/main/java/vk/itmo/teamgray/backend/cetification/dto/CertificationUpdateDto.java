package vk.itmo.teamgray.backend.cetification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@Schema(description = "Certification Update Model")
@Data
public class CertificationUpdateDto {
    @NotNull
    @Schema(description = "Certification ID")
    private long id;

    @NotNull
    @Schema(description = "Resume ID")
    private Long resumeId;

    @NotNull
    @Size(max = 255)
    @Schema(description = "Certification name")
    private String name;

    @NotNull
    @Size(max = 255)
    @Schema(description = "Issuer of the certification")
    private String issuingOrganization;

    @Schema(description = "Issue date of the certification")
    private Date issueDate;

    @Schema(description = "Expiration date of the certification")
    private Date expirationDate;

    @Size(max = 255)
    @Schema(description = "URL providing details about the credential")
    private String credentialUrl;

    @Schema(description = "Proficiency level in the associated language")
    private LanguageProficiency languageProficiency;
}
