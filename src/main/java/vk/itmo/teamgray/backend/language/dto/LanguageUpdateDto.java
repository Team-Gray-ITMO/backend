package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@Schema(description = "Language Record Update Model")
@Data
public class LanguageUpdateDto implements Serializable {
    @NotNull
    @Schema(description = "Language ID")
    private long id;

    @NotNull
    @Schema(description = "Resume ID")
    private Long resumeId;

    @NotNull
    @Schema(description = "Language name")
    private String name;

    @NotNull
    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
