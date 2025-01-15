package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Link Create Model")
public class LinkCreateDto implements Serializable {
    @Schema(description = "Resume ID")
    @NotNull
    @NotEmpty
    private Long resumeId;

    @NotNull
    @Size(max = 255)
    @Schema(description = "Platform name")
    private String platformName;

    @NotNull
    @Size(max = 255)
    @Schema(description = "Profile URL on the platform")
    private String profileUrl;
}
