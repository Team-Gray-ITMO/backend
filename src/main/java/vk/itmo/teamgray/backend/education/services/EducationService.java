package vk.itmo.teamgray.backend.education.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.dto.EducationUpdateDto;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.education.mapper.EducationMapper;
import vk.itmo.teamgray.backend.education.repos.EducationRepository;
import vk.itmo.teamgray.backend.educationinstitution.services.EducationInstitutionService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationService extends BaseService<Education> {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;
    private final EducationInstitutionService educationInstitutionService;
    private final EducationMapper educationMapper;

    @Override
    public Education findEntityById(Long id) {
        return educationRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Education.class, id));
    }

    public EducationDto findById(Long id) {
        return educationMapper.toDto(findEntityById(id));
    }

    public EducationDto createEducation(EducationCreateDto data) {
        var education = new Education(
            data,
            resumeService.findEntityById(data.getResumeId()),
            educationInstitutionService.findEntityById(data.getEducationInstitutionId())
        );

        education = educationRepository.save(education);

        return educationMapper.toDto(education);
    }

    public EducationDto updateEducation(EducationUpdateDto updateDto) {
        var education = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getDegreeType(), education::setDegreeType);
        updated |= updateIfPresent(updateDto.getDegreeName(), education::setDegreeName);
        updated |= updateIfPresent(updateDto.getFieldOfStudy(), education::setFieldOfStudy);
        updated |= updateIfPresent(updateDto.getSpecialization(), education::setSpecialization);
        updated |= updateIfPresent(updateDto.getStartDate(), education::setStartDate);
        updated |= updateIfPresent(updateDto.getEndDate(), education::setEndDate);
        updated |= updateIfPresent(updateDto.getGrade(), education::setGrade);
        updated |= updateIfPresent(updateDto.getEducationInstitutionSubdivision(), education::setEducationInstitutionSubdivision);
        updated |= updateIfPresent(updateDto.getFormat(), education::setFormat);
        updated |= updateIfPresent(updateDto.getAttendanceFormat(), education::setAttendanceFormat);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), education::setResume);
        updated |= educationInstitutionService.updateLinkToEntityIfPresent(updateDto.getEducationInstitutionId(), education::setInstitution);

        if (updated) {
            education = educationRepository.save(education);
        }

        return educationMapper.toDto(education);
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}
