package vk.itmo.teamgray.backend.job.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.job.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.job.dto.CompanyUpdateDto;

//Subject to extension.
@Getter
@Setter
@Entity
@Table(name = "company")
@NoArgsConstructor
public class Company extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public Company(CompanyCreateDto data) {
        name = data.getName();
    }

    public Company(CompanyUpdateDto data) {
        id = data.getId();
        name = data.getName();
    }
}
