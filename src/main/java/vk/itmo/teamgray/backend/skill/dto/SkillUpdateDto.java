package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@NoArgsConstructor
@Data
@Schema(description = "Skill Update Model")
public class SkillUpdateDto {
    @Schema(description = "Unique identifier for the skill")
    private long id;

    @Schema(description = "Resume ID associated with the skill")
    @NotNull
    @NotEmpty
    private Long resumeId;

    @Schema(description = "Name of the skill")
    @NotNull
    @NotEmpty
    private String name;

    @Schema(description = "Proficiency level of the skill")
    @NotNull
    @NotEmpty
    private SkillProficiency proficiency;
}
