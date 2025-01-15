package vk.itmo.teamgray.backend.template.mapper;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.entities.Template;

import static vk.itmo.teamgray.backend.file.FileStorageService.RESUME_TEMPLATE_BUCKET_NAME;

@AllArgsConstructor
@Component
public class TemplateMapper {
    private final FileStorageService fileStorageService;

    private final TemplateMapperInternalImpl internalMapper;

    public TemplateBaseDto toBaseDto(Template entity) {
        return internalMapper.toBaseDto(entity);
    }

    public List<TemplateBaseDto> toBaseDtoList(List<Template> entities) {
        return internalMapper.toBaseDtoList(entities);
    }

    public TemplateDto toDto(Template entity) {
        var dto = internalMapper.toDto(entity);

        FileDto file = fileStorageService.getFile(RESUME_TEMPLATE_BUCKET_NAME, entity.getFilePath());

        dto.setFile(file);

        return dto;
    }

    public List<TemplateDto> toDtoList(List<Template> entities) {
        return entities.stream()
            .map(this::toDto)
            .toList();
    }
}
