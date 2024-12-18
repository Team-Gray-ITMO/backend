package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.link.dto.LinkDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.resume.mapper.ResumeMapper;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.template.services.TemplateService;
import vk.itmo.teamgray.backend.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService extends BaseService<Resume> {
    private final ResumeRepository resumeRepository;

    private final TemplateService templateService;

    private final UserService userService;

    private final ResumeMapper resumeMapper;

    private final ObjectMapper objectMapper;

    public List<ResumeDto> findAll() {
        return resumeMapper.toDtoList(
            resumeRepository.findAllAndFetch(userService.getAuthUser().getId())
        );
    }

    @Override
    public Resume findEntityById(Long id) {
        return resumeRepository.findByIdAndFetch(id, userService.getAuthUser().getId())
            .orElseThrow(() -> DataNotFoundException.entity(Resume.class, id));
    }

    public ResumeDto findById(Long id) {
        return resumeMapper.toDto(findEntityById(id));
    }

    public ResumeDto createResume(ResumeCreateDto data) {
        var authUser = userService.getAuthUser();

        var resume = new Resume();

        resume.setSummary(data.getSummary());
        resume.setUser(authUser);

        resume = resumeRepository.save(resume);

        return resumeMapper.toDto(resume);
    }

    public ResumeDto updateResume(ResumeUpdateDto updateDto) {
        var resume = findEntityById(updateDto.getId());

        boolean updated = templateService.updateLinkToEntityIfPresent(updateDto.getTemplateId(), resume::setTemplate);

        if (updated) {
            resume = resumeRepository.save(resume);
        }

        return resumeMapper.toDto(resume);
    }

    public void deleteById(Long id) {
        var resume = findEntityById(id);
        resumeRepository.delete(resume);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getResumeJsonForMerge(ResumeDto dto) {
        dto.getCertifications().sort(
            Comparator.comparing(CertificationDto::getIssueDate).reversed()
        );

        dto.getEducations().sort(
            Comparator.comparing((EducationDto it) -> it.getDegreeType().ordinal()).reversed()
                .thenComparing(EducationDto::getStartDate).reversed()
        );

        dto.getJobs().sort(
            Comparator.comparing(JobDto::getStartDate).reversed()
        );

        dto.getLinks().sort(
            Comparator.comparing(LinkDto::getPlatformName)
        );

        dto.getSkills().sort(
            Comparator.comparing((SkillDto it) -> it.getProficiency().ordinal()).reversed()
        );

        dto.getLanguages().sort(
            Comparator.comparing((LanguageDto it) -> it.getProficiency().ordinal()).reversed()
        );

        return (Map<String, Object>)objectMapper.convertValue(dto, Map.class);
    }
}
