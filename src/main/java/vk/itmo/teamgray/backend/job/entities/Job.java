package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.company.entities.Company;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Getter
@Setter
@Entity
@Table(name = "job")
@NoArgsConstructor
public class Job extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "attendance_format")
    @Enumerated(EnumType.STRING)
    private JobAttendanceFormat attendanceFormat;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description", length = 2000)
    private String description;

    public Job(JobCreateDto data, Resume resume, Company company) {
        this.resume = resume;
        this.company = company;

        title = data.getTitle();
        description = data.getDescription();
        location = data.getLocation();
        startDate = data.getStartDate();
        endDate = data.getEndDate();
        attendanceFormat = data.getAttendanceFormat();
    }
}
