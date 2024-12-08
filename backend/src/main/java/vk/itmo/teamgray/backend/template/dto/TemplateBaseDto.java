package vk.itmo.teamgray.backend.template.dto;

import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.template.entities.Template;

/**
 * DTO for {@link Template}
 */
@Data
public class TemplateBaseDto {
    private long id;
    private Date createdAt;
    private String name;
    private String filePath;
}
