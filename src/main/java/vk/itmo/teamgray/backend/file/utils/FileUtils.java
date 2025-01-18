package vk.itmo.teamgray.backend.file.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.template.exception.TemplateImportServiceException;

public class FileUtils {
    private FileUtils() {
        //No-op.
    }

    public static Resource getLocalResource(String path) {
        return getLocalResources(path).stream().findFirst().orElseThrow();
    }

    public static List<Resource> getLocalResources(String path) {
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();

        try {
            return Arrays.stream(scanner.getResources(path))
                .filter(Objects::nonNull)
                .filter(Resource::exists)
                .toList();
        } catch (IOException e) {
            throw new TemplateImportServiceException("Could not get resources: ", e);
        }
    }

    public static void validateFileFormat(Set<String> mimeTypes, FileDto file) {
        if (!mimeTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file type: " + file.getContentType());
        }
    }
}
