package vk.itmo.teamgray.backend.template.services;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.file.format.HtmlFormat;
import vk.itmo.teamgray.backend.file.format.ZipFormat;
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

        log.info("Processing templates.");

        processFilesFromClasspath("/templates", file -> {
            var templateCreateDto = formDto(file);

            if (hashes.contains(hash(templateCreateDto.getFile().getContent()))) {
                log.info("Template already imported: {}", file.getFilename());
            } else {
                log.info("Creating new template: {}", file.getFilename());

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

    private void processFilesFromClasspath(String directoryPath, Consumer<FileDto> fileAction) {
        URL resource = this.getClass().getResource(directoryPath);

        if (resource == null) {
            throw new TemplateImportServiceException(directoryPath + " not found");
        }

        var file = new File(resource.getFile());

        if (file.listFiles() == null) {
            throw new TemplateImportServiceException(directoryPath + " returns null.");
        }

        Arrays.stream(Objects.requireNonNull(file.listFiles()))
            .filter(File::isDirectory)
            .map(File::listFiles)
            .filter(Objects::nonNull)
            .filter(fileList -> fileList.length == 1)
            .map(fileList -> fileList[0])
            .filter(singleFile -> singleFile.getName().endsWith(".vm"))
            .forEach(templateFile -> {
                try {
                    fileAction.accept(
                        new FileDto(
                            templateFile.getName(),
                            HtmlFormat.MIME_TYPE,
                            Files.readAllBytes(templateFile.toPath())
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
