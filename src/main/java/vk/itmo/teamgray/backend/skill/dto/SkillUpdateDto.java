package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@NoArgsConstructor
@Data
@Schema(description = "Skill Update Model")
public class SkillUpdateDto {
    @Positive
    @Schema(description = "Unique identifier for the skill")
    private long id;

    @Schema(description = "Resume ID associated with the skill")
    @Positive
    private Long resumeId;

    @Schema(description = "Name of the skill")
    @NotBlankOrNull
    private String name;

    @Schema(description = "Proficiency level of the skill")
    private SkillProficiency proficiency;
}
