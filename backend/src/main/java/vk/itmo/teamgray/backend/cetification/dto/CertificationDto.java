package vk.itmo.teamgray.backend.cetification.dto;

import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@Data
public class CertificationDto {
    private long id;
    private String name;
    private String issuingOrganization;
    private Date issueDate;
    private Date expirationDate;
    private String credentialUrl;
    private LanguageProficiency languageProficiency;
}
