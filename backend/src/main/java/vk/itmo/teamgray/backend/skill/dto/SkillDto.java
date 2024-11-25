package vk.itmo.teamgray.backend.skill.dto;

import lombok.Data;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@Data
public class SkillDto {
    private long id;
    private String name;
    private SkillProficiency proficiency;
}
