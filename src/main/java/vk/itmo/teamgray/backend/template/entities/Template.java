package vk.itmo.teamgray.backend.template.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "template")
@NoArgsConstructor
public class Template extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;
}
