package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Company Update Model")
@Data
public class CompanyUpdateDto {
    @Schema(description = "ID of the company")
    private Long id;

    @Schema(description = "Name of the company")
    private String name;
}
