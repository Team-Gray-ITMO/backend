package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationUpdateDto;
import vk.itmo.teamgray.backend.education.entities.EducationInstitution;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description", length = 2000)
    private String description;

    public Job(JobCreateDto data, Resume resume, Company company){
        this.resume = resume;
        this.company = company;

        title = data.title();
        description = data.description();
        startDate = data.startDate();
        endDate = data.endDate();

    }

    public Job(JobUpdateDto data, Resume resume, Company company){
        id = data.id();
        this.resume = resume;
        this.company = company;

        title = data.title();
        description = data.description();
        startDate = data.startDate();
        endDate = data.endDate();
    }
}
