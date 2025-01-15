package vk.itmo.teamgray.backend.resume.services;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.file.format.JpegFormat;
import vk.itmo.teamgray.backend.file.format.PngFormat;
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

import static vk.itmo.teamgray.backend.file.FileStorageService.RESUME_IMAGE_BUCKET_NAME;
import static vk.itmo.teamgray.backend.file.utils.FileUtils.validateFileFormat;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService extends BaseService<Resume> {
    private final ResumeRepository resumeRepository;

    private final TemplateService templateService;

    private final UserService userService;

    private final ResumeMapper resumeMapper;

    private final FileStorageService fileStorageService;

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

        boolean updated = false;

        updated |= updateImage(resume, updateDto.getImage());

        updated |= updateIfPresent(updateDto.getSummary(), resume::setSummary);
        updated |= updateIfPresent(updateDto.getPreferredAttendanceFormats(), resume::setPreferredAttendanceFormats);
        updated |= updateIfPresent(updateDto.getPreferredSpecialities(), resume::setPreferredSpecialities);
        updated |= updateIfPresent(updateDto.getReadyForBusinessTrips(), resume::setReadyForBusinessTrips);
        updated |= updateIfPresent(updateDto.getReadyForRelocation(), resume::setReadyForRelocation);

        updated |= templateService.updateLinkToEntityIfPresent(updateDto.getTemplateId(), resume::setTemplate);

        if (updated) {
            resume = resumeRepository.save(resume);
        }

        return resumeMapper.toDto(resume);
    }

    private boolean updateImage(Resume resume, FileDto image) {
        if (image == null) {
            return false;
        }

        validateFileFormat(Set.of(PngFormat.MIME_TYPE, JpegFormat.MIME_TYPE), image);

        var filePath = fileStorageService.uploadFile(RESUME_IMAGE_BUCKET_NAME, image);

        resume.setImagePath(filePath);

        return true;
    }

    public void deleteById(Long id) {
        var resume = findEntityById(id);
        resumeRepository.delete(resume);
    }

    public ResumeDto prepareResume(ResumeDto dto) {
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

        return dto;
    }
}
