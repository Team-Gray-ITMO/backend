package vk.itmo.teamgray.backend.language.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.dto.LanguageUpdateDto;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Getter
@Setter
@Entity
@Table(name = "languages")
@NoArgsConstructor
public class Language extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "proficiency", nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageProficiency proficiency;

    public Language(LanguageCreateDto data, Resume resume){
        this.resume = resume;
        name = data.name();
        proficiency = data.proficiency();
    }

    public Language(LanguageUpdateDto data, Resume resume){
        id = data.id();
        this.resume = resume;
        name = data.name();
        proficiency = data.proficiency();
    }
}
