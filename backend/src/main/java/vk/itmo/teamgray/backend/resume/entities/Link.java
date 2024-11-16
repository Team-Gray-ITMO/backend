package vk.itmo.teamgray.backend.resume.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

@Entity
@Table(name = "link")
public class Link extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column
    private String platformName;

    @Column
    private String profileUrl;
}
