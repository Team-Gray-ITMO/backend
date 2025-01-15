package vk.itmo.teamgray.backend.template.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.template.dto.TemplateBaseDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.entities.Template;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TemplateMapperInternal {
    TemplateMapperInternal INSTANCE = Mappers.getMapper(TemplateMapperInternal.class);

    TemplateBaseDto toBaseDto(Template entity);

    List<TemplateBaseDto> toBaseDtoList(List<Template> entities);

    TemplateDto toDto(Template entity);
}
