package vk.itmo.teamgray.backend.cetification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Certification Create Model")
@Data
public class CertificationCreateDto implements Serializable {
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
