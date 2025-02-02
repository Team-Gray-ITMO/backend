package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;

@NoArgsConstructor
@Data
@Schema(description = "Link Update Model")
public class LinkUpdateDto {
    @Positive
    @Schema(description = "Unique identifier for the link")
    private long id;

    @Schema(description = "Resume ID to associate the link with")
    @Positive
    private Long resumeId;

    @NotBlankOrNull
    @Schema(description = "Platform name")
    @Size(max = 255)
    private String platformName;

    @NotBlankOrNull
    @URL
    @Schema(description = "Profile URL on the platform")
    @Size(max = 255)
    private String profileUrl;
}
