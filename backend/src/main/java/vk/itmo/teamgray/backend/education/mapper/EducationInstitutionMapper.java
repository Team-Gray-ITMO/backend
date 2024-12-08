package vk.itmo.teamgray.backend.education.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.education.entities.EducationInstitution;

@Mapper(componentModel = "spring")
public interface EducationInstitutionMapper {
    EducationInstitutionMapper INSTANCE = Mappers.getMapper(EducationInstitutionMapper.class);

    EducationInstitutionDto toDto(EducationInstitution entity);

    List<EducationInstitutionDto> toDtoList(Collection<EducationInstitution> entities);
}
