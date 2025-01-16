package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;

@NoArgsConstructor
@Schema(description = "Job Update Model")
@Data
public class JobUpdateDto {
    @Positive
    @Schema(description = "Job ID")
    private long id;

    @Schema(description = "Resume ID")
    @Positive
    private Long resumeId;

    @Schema(description = "Company ID")
    @Positive
    private Long companyId;

    @Schema(description = "Job title")
    @NotBlank
    @Size(max = 255)
    private String title;

    @Schema(description = "Job location")
    private String location;

    @Schema(description = "Job start date")
    private Date startDate;

    @Schema(description = "Job end date")
    private Date endDate;

    @Schema(description = "Job description")
    @Size(max = 2000)
    private String description;

    @Schema(description = "Job attendance format")
    private JobAttendanceFormat attendanceFormat;
}
