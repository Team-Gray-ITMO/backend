package vk.itmo.teamgray.backend.resume.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.cetification.mapper.CertificationMapper;
import vk.itmo.teamgray.backend.education.mapper.EducationMapper;
import vk.itmo.teamgray.backend.job.mapper.JobMapper;
import vk.itmo.teamgray.backend.language.mapper.LanguageMapper;
import vk.itmo.teamgray.backend.link.mapper.LinkMapper;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.skill.mapper.SkillMapper;

@Mapper(
    componentModel = "spring",
    uses = {
        CertificationMapper.class,
        EducationMapper.class,
        JobMapper.class,
        SkillMapper.class,
        LanguageMapper.class,
        LinkMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ResumeMapperInternal {
    ResumeMapperInternal INSTANCE = Mappers.getMapper(ResumeMapperInternal.class);

    ResumeDto toDto(Resume entity);

    List<ResumeDto> toDtoList(List<Resume> entities);

    ResumeDto dryRun(ResumeCreateDto createDto);
}
