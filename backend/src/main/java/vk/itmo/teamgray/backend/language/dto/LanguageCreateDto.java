package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private Long resumeId;

    @Schema(description = "Language name")
    private String name;

    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
