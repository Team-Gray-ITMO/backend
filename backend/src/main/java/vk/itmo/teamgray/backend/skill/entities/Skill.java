package vk.itmo.teamgray.backend.skill.entities;

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
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.dto.SkillUpdateDto;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@Getter
@Setter
@Entity
@Table(name = "skill")
@NoArgsConstructor
public class Skill extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "proficiency", nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillProficiency proficiency;

    public Skill(SkillCreateDto data, Resume resume) {
        this.resume = resume;
        name = data.getName();
        proficiency = data.getProficiency();
    }

    public Skill(SkillUpdateDto data, Resume resume) {
        id = data.getId();
        this.resume = resume;
        name = data.getName();
        proficiency = data.getProficiency();
    }
}
