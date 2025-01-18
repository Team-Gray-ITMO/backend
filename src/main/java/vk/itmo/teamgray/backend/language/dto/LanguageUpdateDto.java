package vk.itmo.teamgray.backend.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@NoArgsConstructor
@Schema(description = "Language Record Update Model")
@Data
public class LanguageUpdateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -4324388595879736152L;

    @Positive
    @Schema(description = "Language ID")
    private long id;

    @Schema(description = "Resume ID")
    @Positive
    private Long resumeId;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Language name")
    private String name;

    @Schema(description = "Language proficiency level")
    private LanguageProficiency proficiency;
}
