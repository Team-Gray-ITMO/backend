package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@Data
@Schema(description = "Skill Update Model")
public class SkillUpdateDto {

    @Schema(description = "Unique identifier for the skill")
    private Long id;

    @Schema(description = "Resume ID associated with the skill")
    private Long resumeId;

    @Schema(description = "Name of the skill")
    private String name;

    @Schema(description = "Proficiency level of the skill")
    private SkillProficiency proficiency;
}
