package vk.itmo.teamgray.backend.cetification.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.cetification.entities.Certification;

@Mapper(componentModel = "spring")
public interface CertificationMapper {
    CertificationMapper INSTANCE = Mappers.getMapper(CertificationMapper.class);

    CertificationDto toDto(Certification entity);

    List<CertificationDto> toDtoList(List<Certification> entities);
}
