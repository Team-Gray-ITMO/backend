package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;

@NoArgsConstructor
@Data
@Schema(description = "Resume Update Model")
public class ResumeUpdateDto {
    @Schema(description = "Unique identifier for the resume")
    private long id;

    @Schema(description = "Updated summary or objective section of the resume")
    @Max(2000)
    private String summary;

    @Schema(description = "Template ID to be associated with the resume")
    @NotNull
    private Long templateId;

    @Schema(description = "Preferred Attendance Formats")
    private List<JobAttendanceFormat> preferredAttendanceFormats;

    @Schema(description = "Preferred Specialities")
    private List<String> preferredSpecialities;

    @Schema(description = "Ready for Business Trips")
    @NotNull
    private Boolean readyForBusinessTrips;

    @Schema(description = "Ready for Relocation")
    @NotNull
    private Boolean readyForRelocation;
}
