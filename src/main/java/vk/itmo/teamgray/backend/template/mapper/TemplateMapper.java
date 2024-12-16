package vk.itmo.teamgray.backend.template.mapper;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.entities.Template;

@AllArgsConstructor
@Component
public class TemplateMapper {
    private final FileStorageService fileStorageService;

    private static final TemplateMapperInternal INTERNAL_MAPPER = TemplateMapperInternal.INSTANCE;

    public TemplateBaseDto toBaseDto(Template entity) {
        return INTERNAL_MAPPER.toBaseDto(entity);
    }

    public List<TemplateBaseDto> toBaseDtoList(List<Template> entities) {
        return INTERNAL_MAPPER.toBaseDtoList(entities);
    }

    public TemplateDto toDto(Template entity) {
        var dto = INTERNAL_MAPPER.toDto(entity);

        FileDto file = fileStorageService.getFile(entity.getFilePath());

        dto.setFile(file);

        return dto;
    }

    public List<TemplateDto> toDtoList(List<Template> entities) {
        return entities.stream()
            .map(this::toDto)
            .toList();
    }
}
