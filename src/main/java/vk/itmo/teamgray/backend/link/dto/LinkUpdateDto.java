package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@Data
@Schema(description = "Link Update Model")
public class LinkUpdateDto {
    @Schema(description = "Unique identifier for the link")
    private long id;

    @Schema(description = "Resume ID to associate the link with")
    @Positive
    private Long resumeId;

    @Schema(description = "Platform name")
    @Size(max = 255)
    private String platformName;

    @URL
    @Schema(description = "Profile URL on the platform")
    @Size(max = 255)
    private String profileUrl;
}
