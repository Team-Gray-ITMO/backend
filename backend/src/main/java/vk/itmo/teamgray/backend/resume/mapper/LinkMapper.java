package vk.itmo.teamgray.backend.resume.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.resume.dto.LinkDto;
import vk.itmo.teamgray.backend.resume.entities.Link;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkMapper INSTANCE = Mappers.getMapper(LinkMapper.class);

    LinkDto toDto(Link entity);

    List<LinkDto> toDtoList(List<Link> entities);
}
