package vk.itmo.teamgray.backend.template.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.template.dto.FileDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static vk.itmo.teamgray.backend.template.services.TemplateMergeService.INDEX_HTML_FILENAME;

class TemplateMergeServiceTest extends TestBase {
    @Autowired
    private TemplateMergeService templateMergeService;

    @Test
    void testMerge() throws IOException {
        var resume = resumeGenerator.generateResumes(1).getFirst();

        FileDto mergedFile = templateMergeService.mergeTemplate(resume.getId(), sampleTemplate.getId());

        assertNotNull(mergedFile);
        assertEquals(sampleTemplate.getFile().getFilename(), mergedFile.getFilename());
        assertEquals(sampleTemplate.getFile().getContentType(), mergedFile.getContentType());

        Path outputDir = Paths.get("build", "test-output", "merged-template");
        FileSystemUtils.deleteRecursively(outputDir);
        Files.createDirectories(outputDir);

        try (
            ByteArrayInputStream bais = new ByteArrayInputStream(mergedFile.getContent());
            ZipInputStream zis = new ZipInputStream(bais)
        ) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                Path outputFile = outputDir.resolve(entry.getName());
                Files.createDirectories(outputFile.getParent());

                try (OutputStream os = Files.newOutputStream(outputFile)) {
                    IOUtils.copy(zis, os);
                }
            }
        }

        Path extractedIndexHtml = outputDir.resolve(INDEX_HTML_FILENAME);
        assertTrue(Files.exists(extractedIndexHtml));
    }
}
