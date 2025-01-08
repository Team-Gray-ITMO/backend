package vk.itmo.teamgray.backend.company.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.company.dto.CompanyCreateDto;

//Subject to extension.
@Getter
@Setter
@Entity
@Table(name = "company")
@NoArgsConstructor
public class Company extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url")
    private String url;

    public Company(CompanyCreateDto data) {
        name = data.getName();
    }
}
