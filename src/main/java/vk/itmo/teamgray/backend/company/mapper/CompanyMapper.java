package vk.itmo.teamgray.backend.company.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.company.dto.CompanyDto;
import vk.itmo.teamgray.backend.company.entities.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDto toDto(Company entity);

    List<CompanyDto> toDtoList(Collection<Company> entities);
}
