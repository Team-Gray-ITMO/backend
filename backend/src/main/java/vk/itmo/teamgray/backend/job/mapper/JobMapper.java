package vk.itmo.teamgray.backend.job.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.company.mapper.CompanyMapper;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.entities.Job;

@Mapper(
    componentModel = "spring",
    uses = CompanyMapper.class
)
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobDto toDto(Job entity);

    List<JobDto> toDtoList(Collection<Job> entities);
}
