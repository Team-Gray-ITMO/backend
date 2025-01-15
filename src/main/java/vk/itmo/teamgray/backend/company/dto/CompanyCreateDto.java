package vk.itmo.teamgray.backend.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Company Create Model")
@Data
public class CompanyCreateDto {
    @Schema(description = "Name of the company")
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @Schema(description = "Company URL")
    @Size(max = 255)
    private String url;
}
