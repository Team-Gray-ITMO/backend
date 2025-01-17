package vk.itmo.teamgray.backend.cetification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Certification Create Model")
@Data
public class CertificationCreateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7776359123064304987L;

    @Positive
    @Schema(description = "Resume ID")
    private long resumeId;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Certification name")
    private String name;

    @NotBlank
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
