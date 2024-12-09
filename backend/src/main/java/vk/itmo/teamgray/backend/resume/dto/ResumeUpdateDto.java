package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Resume Update Model")
public class ResumeUpdateDto {
    @Schema(description = "Unique identifier for the resume")
    private long id;

    @Schema(description = "Updated summary or objective section of the resume")
    private String summary;

    @Schema(description = "Template ID to be associated with the resume")
    private long templateId;
}
