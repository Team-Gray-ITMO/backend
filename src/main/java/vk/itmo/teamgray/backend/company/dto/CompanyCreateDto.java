package vk.itmo.teamgray.backend.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Company Create Model")
@Data
public class CompanyCreateDto {
    @Schema(description = "Name of the company")
    @NotBlank
    @Size(max = 255)
    private String name;

    @URL
    @Schema(description = "Company URL")
    @Size(max = 255)
    private String url;
}
