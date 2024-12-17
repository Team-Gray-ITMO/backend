package vk.itmo.teamgray.backend.language.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.language.dto.LanguageUpdateDto;
import vk.itmo.teamgray.backend.language.entities.Language;
import vk.itmo.teamgray.backend.language.mapper.LanguageMapper;
import vk.itmo.teamgray.backend.language.repos.LanguageRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageService extends BaseService<Language> {
    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;
    private final LanguageMapper languageMapper;

    @Override
    public Language findEntityById(Long id) {
        return languageRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Language.class, id));
    }

    public LanguageDto findById(Long id) {
        return languageMapper.toDto(findEntityById(id));
    }

    public LanguageDto createLanguage(LanguageCreateDto data) {
        var resume = resumeService.findEntityById(data.getResumeId());

        var language = new Language(data, resume);

        language = languageRepository.save(language);

        return languageMapper.toDto(language);
    }

    public LanguageDto updateLanguage(LanguageUpdateDto updateDto) {
        var language = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getName(), language::setName);
        updated |= updateIfPresent(updateDto.getProficiency(), language::setProficiency);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), language::setResume);

        if (updated) {
            language = languageRepository.save(language);
        }

        return languageMapper.toDto(language);
    }

    public void deleteById(Long id) {
        languageRepository.deleteById(id);
    }
}
