package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@Schema(description = "Language Record Model")
@Data
public class LanguageDto {
    @Schema(description = "Language ID")
    private long id;

    @Schema(description = "Language name")
    private String name;

    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
