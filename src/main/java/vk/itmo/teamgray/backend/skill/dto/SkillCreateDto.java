package vk.itmo.teamgray.backend.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.skill.enums.SkillProficiency;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Skill Create Model")
public class SkillCreateDto {
    @Schema(description = "ID of the resume the skill is associated with")
    @NotNull
    @Positive
    private Long resumeId;

    @Schema(description = "Name of the skill")
    @NotNull
    @NotBlank
    private String name;

    @Schema(description = "Proficiency level of the skill")
    @NotNull
    @NotBlank
    private SkillProficiency proficiency;
}
