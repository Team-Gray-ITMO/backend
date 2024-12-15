package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Resume Create Model")
public class ResumeCreateDto {
    @Schema(description = "User ID associated with the resume")
    private long userId;

    @Schema(description = "Summary or objective of the resume")
    private String summary;
}
