package vk.itmo.teamgray.backend.job.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.job.dto.CompanyDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.entities.Company;
import vk.itmo.teamgray.backend.job.entities.Job;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDto toDto(Company entity);

    List<CompanyDto> toDtoList(Collection<Company> entities);
}
