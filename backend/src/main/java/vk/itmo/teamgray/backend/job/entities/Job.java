package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Entity
@Table(name = "job")
public class Job extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column
    private String location;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column(length = 2000)
    private String description;
}
