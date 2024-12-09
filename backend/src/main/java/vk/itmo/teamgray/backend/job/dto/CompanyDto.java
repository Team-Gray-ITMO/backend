package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Company Model")
@Data
public class CompanyDto {
    @Schema(description = "ID of the company")
    private long id;

    @Schema(description = "Name of the company")
    private String name;
}
