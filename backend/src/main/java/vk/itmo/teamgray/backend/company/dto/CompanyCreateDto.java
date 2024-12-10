package vk.itmo.teamgray.backend.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Company Create Model")
@Data
public class CompanyCreateDto {
    @Schema(description = "Name of the company")
    private String name;
}
