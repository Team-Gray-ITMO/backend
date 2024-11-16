package vk.itmo.teamgray.backend.language.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.language.dto.LanguageProficiency;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Entity
@Table(name = "language")
public class Language extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    //TODO Maybe a fixed enum of known languages?
    @Column
    private String name;

    @Column
    private LanguageProficiency proficiency;
}
