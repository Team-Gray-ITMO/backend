package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@NoArgsConstructor
@Data
@Schema(description = "Skill Model")
public class SkillDto {

    @Schema(description = "Unique identifier for the skill")
    private long id;

    @Schema(description = "Name of the skill")
    private String name;

    @Schema(description = "Proficiency level of the skill")
    private SkillProficiency proficiency;
}
