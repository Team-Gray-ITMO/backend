package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

//Subject to extension.
@Entity
@Table(name = "education_institution")
public class Company extends BaseEntity {
    @Column
    private String name;
}
