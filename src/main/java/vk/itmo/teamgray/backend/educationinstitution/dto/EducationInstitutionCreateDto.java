package vk.itmo.teamgray.backend.educationinstitution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Education Institution Create Model")
@Data
public class EducationInstitutionCreateDto {
    @Schema(description = "Name of the education institution")
    @NotBlank
    @Size(max = 255)
    private String name;
}
