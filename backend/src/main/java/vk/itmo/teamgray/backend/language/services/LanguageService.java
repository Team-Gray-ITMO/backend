package vk.itmo.teamgray.backend.language.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.dto.LanguageUpdateDto;
import vk.itmo.teamgray.backend.language.entities.Language;
import vk.itmo.teamgray.backend.language.repos.LanguageRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;

    public Language findById(Long id){
        return languageRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Language createLanguage(LanguageCreateDto data){
        return languageRepository.save(new Language(
                data,
                resumeService.findById(data.resumeId())
        ));
    }

    public Language updateLanguage(LanguageUpdateDto data){
        return languageRepository.save(new Language(
                data,
                resumeService.findById(data.resumeId())
        ));
    }

    public void deleteById(Long id){
        languageRepository.deleteById(id);
    }
}
