package vk.itmo.teamgray.backend.cetification.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.resume.entities.Resume;

//TODO Add optional language proficiency for language certs
@Entity
@Table(name = "certification")
public class Certification extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column
    private String name;

    @Column
    private String issuingOrganization;

    @Column
    private Date issueDate;

    @Column
    private Date expirationDate;

    @Column
    private String credentialUrl;
}
