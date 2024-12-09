package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Education Institution Model")
@Data
public class EducationInstitutionDto {
    @Schema(description = "ID of the education institution")
    private long id;

    @Schema(description = "Name of the education institution")
    private String name;
}
