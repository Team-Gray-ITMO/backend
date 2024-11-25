package vk.itmo.teamgray.backend.skill.dto;

import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.skill.entities.Skill}
 */
public record SkillUpdateDto(
        Long id,
        Long resumeId,
        String name,
        SkillProficiency proficiency
) implements Serializable {
}