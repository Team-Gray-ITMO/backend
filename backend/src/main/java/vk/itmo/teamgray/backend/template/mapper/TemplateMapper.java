package vk.itmo.teamgray.backend.template.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.template.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.entities.Template;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateMapper {
    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    TemplateBaseDto toBaseDto(Template entity);

    List<TemplateBaseDto> toBaseDtoList(List<Template> entities);

    TemplateDto toDto(Template template);

    default TemplateDto toDto(Template entity, FileDto file) {
        var dto = toDto(entity);

        dto.setFile(file);

        return dto;
    }
}
