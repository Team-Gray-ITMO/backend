package vk.itmo.teamgray.backend.cetification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@Schema(description = "Certification Model")
@Data
public class CertificationDto {
    @Schema(description = "Unique identifier of the certification")
    private long id;

    @Schema(description = "Name of the certification")
    private String name;

    @Schema(description = "Organization that issued the certification")
    private String issuingOrganization;

    @Schema(description = "Date when the certification was issued")
    private Date issueDate;

    @Schema(description = "Date when the certification expires")
    private Date expirationDate;

    @Schema(description = "URL with details about the certification")
    private String credentialUrl;

    @Schema(description = "Proficiency level of the associated language")
    private LanguageProficiency languageProficiency;
}
