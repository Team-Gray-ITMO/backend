package vk.itmo.teamgray.backend.education.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.entities.Education;

@Mapper(
    componentModel = "spring",
    uses = EducationInstitutionMapper.class
)
public interface EducationMapper {
    EducationMapper INSTANCE = Mappers.getMapper(EducationMapper.class);

    EducationDto toDto(Education entity);

    List<EducationDto> toDtoList(Collection<Education> entities);
}
