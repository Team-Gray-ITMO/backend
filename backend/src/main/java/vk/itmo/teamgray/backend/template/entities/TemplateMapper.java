package vk.itmo.teamgray.backend.template.entities;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateMapper {
    TemplateDto toDto(Template template);
}