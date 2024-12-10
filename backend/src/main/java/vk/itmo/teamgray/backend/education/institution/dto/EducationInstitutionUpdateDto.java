package vk.itmo.teamgray.backend.education.institution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Education Institution Update Model")
@Data
public class EducationInstitutionUpdateDto {
    @Schema(description = "ID of the education institution")
    private Long id;

    @Schema(description = "Name of the education institution")
    private String name;
}
