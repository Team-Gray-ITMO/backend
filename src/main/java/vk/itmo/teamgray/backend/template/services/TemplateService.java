package vk.itmo.teamgray.backend.template.services;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.format.ZipFormat;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateUpdateDto;
import vk.itmo.teamgray.backend.template.entities.Template;
import vk.itmo.teamgray.backend.template.mapper.TemplateMapper;
import vk.itmo.teamgray.backend.template.repos.TemplateRepository;

import static vk.itmo.teamgray.backend.file.hash.HashUtils.hash;

@RequiredArgsConstructor
@Service
@Transactional
public class TemplateService extends BaseService<Template> {
    private final TemplateRepository templateRepository;

    private final FileStorageService fileStorageService;

    private final TemplateMapper templateMapper;

    public List<TemplateDto> findAll() {
        return templateMapper.toDtoList(templateRepository.findAll());
    }

    public TemplateDto findById(Long id) {
        return templateMapper.toDto(findEntityById(id));
    }

    @Override
    public Template findEntityById(Long id) {
        return templateRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Template.class, id));
    }

    public TemplateDto createTemplate(TemplateCreateDto dto) {
        var file = dto.getFile();

        if (!Objects.equals(file.getContentType(), ZipFormat.MIME_TYPE)) {
            throw new IllegalArgumentException("Invalid file type: " + file.getContentType());
        }

        String hash = hash(file.getContent());

        var filePath = fileStorageService.uploadFile(file);

        var template = new Template();
        template.setName(dto.getName());
        template.setCreatedAt(Instant.now());
        template.setFilePath(filePath);
        template.setFileHash(hash);

        template = templateRepository.save(template);

        return templateMapper.toDto(template);
    }

    public TemplateDto updateTemplate(TemplateUpdateDto dto) {
        var template = findEntityById(dto.getId());

        boolean updated = false;

        updated |= updateIfPresent(dto.getName(), template::setName);

        if (dto.getFile() != null) {
            var file = dto.getFile();

            String hash = hash(file.getContent());

            if (!Objects.equals(hash, template.getFileHash())) {
                var filePath = fileStorageService.uploadFile(file);

                template.setFilePath(filePath);
                template.setFileHash(hash);

                updated = true;
            }
        }

        if (updated) {
            template = templateRepository.save(template);
        }

        return templateMapper.toDto(template);
    }

    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    public Set<String> getAllTemplateHashes() {
        return templateRepository.getAllTemplateHashes();
    }
}
