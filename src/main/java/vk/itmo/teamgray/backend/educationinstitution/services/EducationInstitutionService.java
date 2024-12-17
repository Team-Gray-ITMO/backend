package vk.itmo.teamgray.backend.educationinstitution.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionUpdateDto;
import vk.itmo.teamgray.backend.educationinstitution.entities.EducationInstitution;
import vk.itmo.teamgray.backend.educationinstitution.mapper.EducationInstitutionMapper;
import vk.itmo.teamgray.backend.educationinstitution.repos.EducationInstitutionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationInstitutionService extends BaseService<EducationInstitution> {
    private final EducationInstitutionRepository educationInstitutionRepository;

    private final EducationInstitutionMapper educationInstitutionMapper;

    @Override
    public EducationInstitution findEntityById(Long id) {
        return educationInstitutionRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(EducationInstitution.class, id));
    }

    public EducationInstitutionDto findById(Long id) {
        return educationInstitutionMapper.toDto(findEntityById(id));
    }

    public EducationInstitutionDto createEducationInstitution(EducationInstitutionCreateDto data) {
        var educationInstitution = new EducationInstitution(data);

        educationInstitution = educationInstitutionRepository.save(educationInstitution);

        return educationInstitutionMapper.toDto(educationInstitution);
    }

    public EducationInstitutionDto updateEducationInstitution(EducationInstitutionUpdateDto updateDto) {
        var educationInstitution = findEntityById(updateDto.getId());

        boolean updated = updateIfPresent(updateDto.getName(), educationInstitution::setName);

        if (updated) {
            educationInstitution = educationInstitutionRepository.save(educationInstitution);
        }

        return educationInstitutionMapper.toDto(educationInstitution);
    }

    public void deleteById(Long id) {
        educationInstitutionRepository.deleteById(id);
    }
}
