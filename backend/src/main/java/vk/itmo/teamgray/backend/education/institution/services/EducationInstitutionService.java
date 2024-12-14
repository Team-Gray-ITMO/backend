package vk.itmo.teamgray.backend.education.institution.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.education.institution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.institution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.education.institution.dto.EducationInstitutionUpdateDto;
import vk.itmo.teamgray.backend.education.institution.entities.EducationInstitution;
import vk.itmo.teamgray.backend.education.institution.mapper.EducationInstitutionMapper;
import vk.itmo.teamgray.backend.education.institution.repos.EducationInstitutionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationInstitutionService extends BaseService<EducationInstitution> {
    private final EducationInstitutionRepository educationInstitutionRepository;

    private final EducationInstitutionMapper educationInstitutionMapper;

    @Override
    public EducationInstitution findEntityById(Long id) {
        return educationInstitutionRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public EducationInstitutionDto findById(Long id) {
        return educationInstitutionMapper.toDto(findEntityById(id));
    }

    public EducationInstitutionDto createEducationInstitution(EducationInstitutionCreateDto data) {
        return createEducationInstitution(data, true);
    }

    public EducationInstitutionDto createEducationInstitution(EducationInstitutionCreateDto data, boolean persist) {
        var educationInstitution = new EducationInstitution(data);

        if (persist) {
            educationInstitution = educationInstitutionRepository.save(educationInstitution);
        }

        return educationInstitutionMapper.toDto(educationInstitution);
    }

    public EducationInstitutionDto updateEducationInstitution(EducationInstitutionUpdateDto data) {
        return educationInstitutionMapper.toDto(
            educationInstitutionRepository.save(new EducationInstitution(
                data
            ))
        );
    }

    public void deleteById(Long id) {
        educationInstitutionRepository.deleteById(id);
    }
}
