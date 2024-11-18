package vk.itmo.teamgray.backend.cetification.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationUpdateDto;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.language.dto.LanguageProficiency;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Entity
@Getter
@Setter
@Table(name = "certification")
@NoArgsConstructor
public class Certification extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "issuing_organization", nullable = false)
    private String issuingOrganization;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "credential_url")
    private String credentialUrl;

    @Column(name = "language_proficiency")
    @Enumerated(EnumType.STRING)
    private LanguageProficiency languageProficiency;

    public Certification(CertificationCreateDto data, Resume resume){
        name = data.name();
        issuingOrganization = data.issuingOrganization();
        issueDate = data.issueDate();
        expirationDate = data.expirationDate();
        credentialUrl = data.credentialUrl();
        languageProficiency = data.languageProficiency();
        this.resume = resume;
    }

    public Certification(CertificationUpdateDto data, Resume resume){
        this.id = data.id();
        name = data.name();
        issuingOrganization = data.issuingOrganization();
        issueDate = data.issueDate();
        expirationDate = data.expirationDate();
        credentialUrl = data.credentialUrl();
        languageProficiency = data.languageProficiency();
        this.resume = resume;
    }
}
