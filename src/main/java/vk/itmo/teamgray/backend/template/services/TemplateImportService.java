package vk.itmo.teamgray.backend.template.services;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.file.format.HtmlFormat;
import vk.itmo.teamgray.backend.file.format.VmFormat;
import vk.itmo.teamgray.backend.file.format.ZipFormat;
import vk.itmo.teamgray.backend.file.utils.FileUtils;
import vk.itmo.teamgray.backend.file.utils.ZipUtils;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.exception.TemplateImportServiceException;

import static vk.itmo.teamgray.backend.file.hash.HashUtils.hash;
import static vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService.INDEX_HTML_FILENAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateImportService {
    private final TemplateService templateService;

    @PostConstruct
    public void init() {
        processFiles();
    }

    private void processFiles() {
        var hashes = templateService.getAllTemplateHashes();

        var filePaths = templateService.getAllTemplateFilePaths();

        log.info("Processing templates.");

        processTemplatesFromClasspath("/templates", file -> {
            var templateCreateDto = formDto(file);

            String filename = file.getFilename();

            if (hashes.contains(hash(templateCreateDto.getFile().getContent()))) {
                log.info("Template already imported: {}", filename);
            } else {
                var existingPath = filePaths.stream().filter(filePath -> filePath.contains(filename)).findAny();

                if (existingPath.isPresent()) {
                    log.info("Updating existing template: {}", filename);

                    templateService.dropTemplateByPath(existingPath.get());
                } else {
                    log.info("Creating new template: {}", filename);
                }

                templateService.createTemplate(templateCreateDto);
            }
        });
    }

    private static TemplateCreateDto formDto(FileDto file) {
        return new TemplateCreateDto(
            makeFileNamePretty(file.getFilename()),
            new FileDto(
                stripFileName(file.getFilename()) + ZipFormat.EXTENSION,
                ZipFormat.MIME_TYPE,
                ZipUtils.repackZip(
                    Map.of(INDEX_HTML_FILENAME, file.getContent())
                )
            )
        );
    }

    private void processTemplatesFromClasspath(String directoryPath, Consumer<FileDto> fileAction) {
        var resources = FileUtils.getLocalResources("classpath*:" + directoryPath + "/**/*" + VmFormat.EXTENSION);

        if (resources.isEmpty()) {
            throw new TemplateImportServiceException(directoryPath + " is empty.");
        }

        resources.forEach(templateFile -> {
                try {
                    fileAction.accept(
                        new FileDto(
                            templateFile.getFilename(),
                            HtmlFormat.MIME_TYPE,
                            templateFile.getContentAsByteArray()
                        )
                    );
                } catch (IOException e) {
                    throw new TemplateImportServiceException("Could not process file", e);
                }
            });
    }

    private static String makeFileNamePretty(String fileName) {
        return Arrays.stream(stripFileName(fileName).split("_"))
            .map(String::trim)
            .filter(StringUtils::isNotBlank)
            .map(TemplateImportService::uppercaseFirstLetter)
            .collect(Collectors.joining(" "));
    }

    private static String uppercaseFirstLetter(String word) {
        return Character.toTitleCase(word.charAt(0)) + word.substring(1);
    }

    private static String stripFileName(String path) {
        return FilenameUtils.removeExtension(new File(path).getName());
    }
}
