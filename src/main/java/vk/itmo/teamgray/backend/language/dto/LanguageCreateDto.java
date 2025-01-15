package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    private Long resumeId;

    @Schema(description = "Language name")
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String name;

    @Schema(description = "Language proficiency level")
    @NotNull
    @NotEmpty
    private LanguageProficiency proficiency;
}
