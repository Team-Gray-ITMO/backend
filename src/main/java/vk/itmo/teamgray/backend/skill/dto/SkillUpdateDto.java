package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive
    private Long resumeId;

    @Schema(description = "Name of the skill")
    @NotBlank
    private String name;

    @Schema(description = "Proficiency level of the skill")
    @NotBlank
    private SkillProficiency proficiency;
}
