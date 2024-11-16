package vk.itmo.teamgray.backend.skill.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.skill.dto.SkillProficiency;

@Entity
@Table(name = "skill")
public class Skill extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private SkillProficiency proficiency;
}
