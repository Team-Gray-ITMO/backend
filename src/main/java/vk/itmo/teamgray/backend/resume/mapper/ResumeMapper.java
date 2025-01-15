package vk.itmo.teamgray.backend.resume.mapper;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.file.FileStorageService;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;

import static vk.itmo.teamgray.backend.file.FileStorageService.RESUME_IMAGE_BUCKET_NAME;

@AllArgsConstructor
@Component
public class ResumeMapper {
    private final FileStorageService fileStorageService;

    private final ResumeMapperInternal internalMapper;

    public ResumeDto toDto(Resume entity) {
        var dto = internalMapper.toDto(entity);

        String imagePath = entity.getImagePath();

        if (StringUtils.isNotBlank(imagePath)) {
            FileDto file = fileStorageService.getFile(RESUME_IMAGE_BUCKET_NAME, imagePath);
            dto.setImage(file);
        }

        return dto;
    }

    public List<ResumeDto> toDtoList(List<Resume> entities) {
        return entities.stream()
            .map(this::toDto)
            .toList();
    }

    public ResumeDto dryRun(ResumeCreateDto createDto) {
        return internalMapper.dryRun(createDto);
    }
}
