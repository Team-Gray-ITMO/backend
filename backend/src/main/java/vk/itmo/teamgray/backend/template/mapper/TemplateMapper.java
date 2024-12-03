package vk.itmo.teamgray.backend.template.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.entities.Template;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    TemplateBaseDto toDto(Template entity);

    List<TemplateBaseDto> toDtoList(List<Template> entities);
}
