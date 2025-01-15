package vk.itmo.teamgray.backend.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;

@NoArgsConstructor
@Data
@Schema(description = "Resume Update Model")
public class ResumeUpdateDto {
    @Schema(description = "Unique identifier for the resume")
    private long id;

    @Schema(description = "Updated summary or objective section of the resume")
    private String summary;

    @Schema(description = "Template ID to be associated with the resume")
    private Long templateId;

    @Schema(description = "Preferred Attendance Format")
    private JobAttendanceFormat preferredAttendanceFormat;

    @Schema(description = "Preferred Specialities")
    private List<String> preferredSpecialities;

    @Schema(description = "Ready for Business Trips")
    private Boolean readyForBusinessTrips;

    @Schema(description = "Ready for Relocation")
    private Boolean readyForRelocation;

    @Schema(description = "Resume related image (photo, avatar)")
    private FileDto image;
}
