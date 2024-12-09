package vk.itmo.teamgray.backend.language.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
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
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;
    private final LanguageMapper languageMapper;

    public Language findEntityById(Long id) {
        return languageRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public LanguageDto findById(Long id) {
        return languageMapper.toDto(findEntityById(id));
    }

    public LanguageDto createLanguage(LanguageCreateDto data) {
        return createLanguage(data, true);
    }

    public LanguageDto createLanguage(LanguageCreateDto data, boolean persist) {
        var resume = resumeService.findEntityById(data.getResumeId());

        var language = new Language(data, resume);

        if (persist) {
            language = languageRepository.save(language);
        }

        return languageMapper.toDto(language);
    }

    public LanguageDto updateLanguage(LanguageUpdateDto data) {
        return languageMapper.toDto(
            languageRepository.save(new Language(
                data,
                resumeService.findEntityById(data.getResumeId())
            ))
        );
    }

    public void deleteById(Long id) {
        languageRepository.deleteById(id);
    }
}
