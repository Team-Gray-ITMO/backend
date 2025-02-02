package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Positive
    private long resumeId;

    @Schema(description = "Language name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
