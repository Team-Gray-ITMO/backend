package vk.itmo.teamgray.backend.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateUpdateDto {
    private long id;

    private String name;

    private FileDto file;
}
