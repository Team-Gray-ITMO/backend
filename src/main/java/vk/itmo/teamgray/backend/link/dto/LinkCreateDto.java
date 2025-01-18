package vk.itmo.teamgray.backend.link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Link Create Model")
public class LinkCreateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8387536203797695402L;

    @Schema(description = "Resume ID")
    @Positive
    private long resumeId;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Platform name")
    private String platformName;

    @NotBlank
    @URL
    @Size(max = 255)
    @Schema(description = "Profile URL on the platform")
    private String profileUrl;
}
