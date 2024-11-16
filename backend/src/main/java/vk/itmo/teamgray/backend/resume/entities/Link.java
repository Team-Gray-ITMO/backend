package vk.itmo.teamgray.backend.resume.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "link")
public class Link extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "profile_url", nullable = false)
    private String profileUrl;
}
