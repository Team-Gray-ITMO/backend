package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

//Subject to extension.
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}
