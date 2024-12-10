package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Link Update Model")
public class LinkUpdateDto {
    @Schema(description = "Unique identifier for the link")
    private Long id;

    @Schema(description = "Resume ID to associate the link with")
    private Long resumeId;

    @Schema(description = "Platform name")
    private String platformName;

    @Schema(description = "Profile URL on the platform")
    private String profileUrl;
}
