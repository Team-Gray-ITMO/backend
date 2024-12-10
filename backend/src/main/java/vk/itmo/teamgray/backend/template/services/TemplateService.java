package vk.itmo.teamgray.backend.template.services;

import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateUpdateDto;
import vk.itmo.teamgray.backend.template.entities.Template;
import vk.itmo.teamgray.backend.template.mapper.TemplateMapper;
import vk.itmo.teamgray.backend.template.repos.TemplateRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;

    private final FileStorageService fileStorageService;

    private final TemplateMapper templateMapper;

    public List<TemplateDto> findAll() {
        return templateMapper.toDtoList(templateRepository.findAll());
    }

    public TemplateDto findById(Long id) {
        return getTemplateDto(findEntityById(id));
    }

    private Template findEntityById(Long id) {
        return templateRepository.findById(id)
            .orElseThrow(ModelNotFoundException::new);
    }

    public TemplateDto createTemplate(TemplateCreateDto dto) {
        return createTemplate(dto, true);
    }

    public TemplateDto createTemplate(TemplateCreateDto dto, boolean persist) {
        var file = dto.getFile();

        var filePath = fileStorageService.uploadFile(file);

        var template = new Template();
        template.setName(dto.getName());
        template.setCreatedAt(Instant.now());
        template.setFilePath(filePath);

        if (persist) {
            template = templateRepository.save(template);
        }

        return getTemplateDto(template);
    }

    public TemplateDto updateTemplate(TemplateUpdateDto dto) {
        var template = findEntityById(dto.getId());

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

    public TemplateDto getTemplateDto(Template template) {
        FileDto file = fileStorageService.getFile(template.getFilePath());

        return templateMapper.toDto(template, file);
    }
}
