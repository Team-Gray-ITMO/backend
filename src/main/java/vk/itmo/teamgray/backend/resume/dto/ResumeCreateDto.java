package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Resume Create Model")
public class ResumeCreateDto {
    @Schema(description = "Summary or objective of the resume")
    @Size(max = 2000)
    private String summary;
}
