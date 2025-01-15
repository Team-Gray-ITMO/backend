package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@Schema(description = "Language Record Update Model")
@Data
public class LanguageUpdateDto implements Serializable {
    @Schema(description = "Language ID")
    private long id;

    @Schema(description = "Resume ID")
    @NotEmpty
    private Long resumeId;

    @NotEmpty
    @Size(min = 1, max = 255)
    @Schema(description = "Language name")
    private String name;

    @NotEmpty
    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
