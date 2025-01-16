package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@Schema(description = "Language Record Update Model")
@Data
public class LanguageUpdateDto implements Serializable {
    @Positive
    @Schema(description = "Language ID")
    private long id;

    @Schema(description = "Resume ID")
    @Positive
    private Long resumeId;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Language name")
    private String name;

    @NotBlank
    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
