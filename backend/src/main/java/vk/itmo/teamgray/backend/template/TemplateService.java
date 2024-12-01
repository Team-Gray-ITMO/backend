package vk.itmo.teamgray.backend.template;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.template.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateUpdateDto;
import vk.itmo.teamgray.backend.template.entities.Template;
import vk.itmo.teamgray.backend.template.repos.TemplateRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;

    private final FileStorageService fileStorageService;

    public TemplateDto findById(Long id) {
        return getTemplateDto(getEntityById(id));
    }

    public TemplateDto createTemplate(TemplateCreateDto dto) {
        var file = dto.getFile();

        var filePath = fileStorageService.uploadFile(file);

        var template = new Template();

        template.setName(dto.getName());
        template.setCreatedAt(Instant.now());
        template.setFilePath(filePath);

        return getTemplateDto(templateRepository.save(template));
    }

    public TemplateDto updateTemplate(TemplateUpdateDto dto) {
        var template = getEntityById(dto.getId());

        if (dto.getFile() != null) {
            var file = dto.getFile();

            var filePath = fileStorageService.uploadFile(file);

            template.setFilePath(filePath);
        }

        if (dto.getName() != null) {
            template.setName(dto.getName());
        }

        return getTemplateDto(templateRepository.save(template));
    }

    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    private Template getEntityById(Long id) {
        return templateRepository.findById(id)
            .orElseThrow(ModelNotFoundException::new);
    }

    private TemplateDto getTemplateDto(Template template) {
        FileDto file = fileStorageService.getFile(template.getFilePath());

        return new TemplateDto(
            template.getId(),
            template.getName(),
            file
        );
    }
}
