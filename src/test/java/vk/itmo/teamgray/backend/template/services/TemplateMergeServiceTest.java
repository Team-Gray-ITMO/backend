package vk.itmo.teamgray.backend.template.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
import vk.itmo.teamgray.backend.file.dto.FileDto;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService.INDEX_HTML_FILENAME;

class TemplateMergeServiceTest extends TestBase {
    @Autowired
    private TemplateMergeService templateMergeService;

    @Test
    void testMerge() throws IOException {
        var sampleTemplate = sampleTemplates.getLast();

        var resume = resumeGenerator.generateResumes(1, sampleTemplate.getId()).getFirst();

        FileDto mergedFile = templateMergeService.mergeTemplateToZip(resume.getId(), sampleTemplate.getId());

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

    @Test
    void testToHtml() {
        var resume = resumeGenerator.generateResumes(1, sampleTemplates.getFirst().getId()).getFirst();

        byte[] mergedHtml = templateMergeService.mergeTemplateToHtml(resume);

        String htmlContent = new String(mergedHtml, StandardCharsets.UTF_8);
        assertNotNull(htmlContent);
        assertTrue(htmlContent.contains("email@example.com"));
        assertTrue(htmlContent.contains("Инженер-программист с 9000+ лет опыта"));
        assertTrue(htmlContent.contains("https://github.com"));
        assertTrue(htmlContent.contains("https://linkedin.com"));
        assertTrue(htmlContent.contains("JPA 1"));
        assertTrue(htmlContent.contains("Java 1"));
        assertTrue(htmlContent.contains("Hibernate 1"));
        assertTrue(htmlContent.contains("Kotlin 1"));
        assertTrue(htmlContent.contains("Microsoft"));
        assertTrue(htmlContent.contains("Google"));
        assertTrue(htmlContent.contains("ФГБОУ Высшее 1"));
        assertTrue(htmlContent.contains("ФГБОУ Среднее Специальное 1"));
        assertTrue(htmlContent.contains("ФГБОУ Среднее 1"));
        assertTrue(htmlContent.contains("ФГБОУ Начальное 1"));
        assertTrue(htmlContent.contains("ФГБОУ Другое 1"));
        assertTrue(htmlContent.contains("IELTS"));
        assertTrue(htmlContent.contains("Oracle"));
        assertTrue(htmlContent.contains("German"));
        assertTrue(htmlContent.contains("Russian"));
        assertTrue(htmlContent.contains("English"));
    }
}
