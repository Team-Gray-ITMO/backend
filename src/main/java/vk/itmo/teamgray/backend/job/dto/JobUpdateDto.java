package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
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
    @NotBlankOrNull
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
