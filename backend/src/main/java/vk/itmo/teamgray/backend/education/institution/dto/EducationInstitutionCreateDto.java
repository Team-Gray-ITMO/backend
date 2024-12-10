package vk.itmo.teamgray.backend.education.institution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Education Institution Create Model")
@Data
public class EducationInstitutionCreateDto {
    @Schema(description = "Name of the education institution")
    private String name;
}
