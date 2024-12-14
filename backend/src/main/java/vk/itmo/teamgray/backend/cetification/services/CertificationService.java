package vk.itmo.teamgray.backend.cetification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationUpdateDto;
import vk.itmo.teamgray.backend.cetification.entities.Certification;
import vk.itmo.teamgray.backend.cetification.mapper.CertificationMapper;
import vk.itmo.teamgray.backend.cetification.repos.CertificationRepository;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService extends BaseService<Certification> {
    private final CertificationRepository certificationRepository;
    private final ResumeService resumeService;
    private final CertificationMapper certificationMapper;

    @Override
    public Certification findEntityById(Long id) {
        return certificationRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Certification.class, id));
    }

    public CertificationDto findById(Long id) {
        return certificationMapper.toDto(findEntityById(id));
    }

    public CertificationDto createCertification(CertificationCreateDto data) {
        return createCertification(data, true);
    }

    public CertificationDto createCertification(CertificationCreateDto data, boolean persist) {
        var resume = resumeService.findEntityById(data.getResumeId());
        var certification = new Certification(data, resume);

        if (persist) {
            certification = certificationRepository.save(certification);
        }

        return certificationMapper.toDto(certification);
    }

    public CertificationDto updateCertification(CertificationUpdateDto updateDto) {
        var certification = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getName(), certification::setName);
        updated |= updateIfPresent(updateDto.getIssuingOrganization(), certification::setIssuingOrganization);
        updated |= updateIfPresent(updateDto.getIssueDate(), certification::setIssueDate);
        updated |= updateIfPresent(updateDto.getExpirationDate(), certification::setExpirationDate);
        updated |= updateIfPresent(updateDto.getCredentialUrl(), certification::setCredentialUrl);
        updated |= updateIfPresent(updateDto.getLanguageProficiency(), certification::setLanguageProficiency);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), certification::setResume);

        if (updated) {
            certification = certificationRepository.save(certification);
        }

        return certificationMapper.toDto(certification);
    }

    public void deleteById(Long id) {
        certificationRepository.deleteById(id);
    }
}
