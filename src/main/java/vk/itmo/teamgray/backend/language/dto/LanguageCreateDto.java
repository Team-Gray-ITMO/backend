package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Language Record Create Model")
@Data
public class LanguageCreateDto {
    @Schema(description = "Resume ID")
    @NotNull
    @Positive
    private Long resumeId;

    @Schema(description = "Language name")
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "Language proficiency level")
    @NotNull
    @NotBlank
    private LanguageProficiency proficiency;
}
