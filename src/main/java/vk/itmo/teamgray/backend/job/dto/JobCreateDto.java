package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Job Create Model")
@Data
public class JobCreateDto {
    @Schema(description = "ID of the resume")
    @NotNull
    @Positive
    private long resumeId;

    @Schema(description = "ID of the company")
    @Positive
    private long companyId;

    @Schema(description = "Job title")
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlankOrNull
    @Schema(description = "Job location")
    private String location;

    @Past
    @Schema(description = "Job start date")
    private Date startDate;

    @Schema(description = "Job end date")
    private Date endDate;

    @NotBlankOrNull
    @Schema(description = "Job description")
    @Size(max = 2000)
    private String description;

    @Schema(description = "Job attendance format")
    private JobAttendanceFormat attendanceFormat;
}
