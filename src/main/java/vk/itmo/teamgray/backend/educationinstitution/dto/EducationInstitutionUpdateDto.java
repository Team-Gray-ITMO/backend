package vk.itmo.teamgray.backend.educationinstitution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Schema(description = "Education Institution Update Model")
@Data
public class EducationInstitutionUpdateDto {
    @Schema(description = "ID of the education institution")
    private long id;

    @Schema(description = "Name of the education institution")
    @NotBlank
    @Size(max = 255)
    private String name;
}
