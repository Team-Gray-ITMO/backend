package vk.itmo.teamgray.backend.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Job Create Model")
@Data
public class JobCreateDto {
    @Schema(description = "ID of the resume")
    private Long resumeId;

    @Schema(description = "ID of the company")
    private Long companyId;

    @Schema(description = "Job title")
    private String title;

    @Schema(description = "Job location")
    private String location;

    @Schema(description = "Job start date")
    private Date startDate;

    @Schema(description = "Job end date")
    private Date endDate;

    @Schema(description = "Job description")
    private String description;

    @Schema(description = "Job attendance format")
    private JobAttendanceFormat attendanceFormat;
}
