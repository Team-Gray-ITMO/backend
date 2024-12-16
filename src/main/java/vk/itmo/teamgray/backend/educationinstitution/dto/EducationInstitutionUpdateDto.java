package vk.itmo.teamgray.backend.educationinstitution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Schema(description = "Education Institution Update Model")
@Data
public class EducationInstitutionUpdateDto {
    @Schema(description = "ID of the education institution")
    private long id;

    @Schema(description = "Name of the education institution")
    private String name;
}
