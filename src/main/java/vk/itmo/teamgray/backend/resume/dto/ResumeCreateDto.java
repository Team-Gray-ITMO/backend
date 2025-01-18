package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Resume Create Model")
public class ResumeCreateDto {
    @NotBlankOrNull
    @Schema(description = "Resume Title")
    @Size(max = 255)
    private String title;

    @NotBlankOrNull
    @Schema(description = "Summary or objective of the resume")
    @Size(max = 2000)
    private String summary;
}
