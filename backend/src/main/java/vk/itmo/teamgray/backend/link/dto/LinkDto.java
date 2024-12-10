package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Link Model")
public class LinkDto {
    @Schema(description = "Unique identifier for the link")
    private long id;

    @Schema(description = "Platform name")
    private String platformName;

    @Schema(description = "Profile URL on the platform")
    private String profileUrl;
}
