package vk.itmo.teamgray.backend.link.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.link.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.link.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Getter
@Setter
@Entity
@Table(name = "link")
@NoArgsConstructor
public class Link extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "profile_url", nullable = false)
    private String profileUrl;

    public Link(LinkCreateDto data, Resume resume) {
        this.resume = resume;
        platformName = data.getPlatformName();
        profileUrl = data.getProfileUrl();
    }

    public Link(LinkUpdateDto data, Resume resume) {
        id = data.getId();
        this.resume = resume;
        platformName = data.getPlatformName();
        profileUrl = data.getProfileUrl();
    }
}
