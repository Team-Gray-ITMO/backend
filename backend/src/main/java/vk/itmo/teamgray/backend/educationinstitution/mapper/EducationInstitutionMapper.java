package vk.itmo.teamgray.backend.educationinstitution.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.educationinstitution.entities.EducationInstitution;

@Mapper(componentModel = "spring")
public interface EducationInstitutionMapper {
    EducationInstitutionMapper INSTANCE = Mappers.getMapper(EducationInstitutionMapper.class);

    EducationInstitutionDto toDto(EducationInstitution entity);

    List<EducationInstitutionDto> toDtoList(Collection<EducationInstitution> entities);
}
