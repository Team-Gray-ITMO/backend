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
        var resume = resumeGenerator.generateResumes(1, sampleTemplate.getId()).getFirst();

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

    @Test
    void testToHtml() {
        var resume = resumeGenerator.generateResumes(1, sampleTemplate.getId()).getFirst();

        byte[] mergedHtml = templateMergeService.mergeTemplateToHtml(resume);

        String htmlContent = new String(mergedHtml, StandardCharsets.UTF_8);
        assertNotNull(htmlContent);
        assertTrue(htmlContent.contains("email1@example.com"));
        assertTrue(htmlContent.contains("Test Summary 1"));
        assertTrue(htmlContent.contains("https://github.com"));
        assertTrue(htmlContent.contains("https://linkedin.com"));
        assertTrue(htmlContent.contains("Skill EXPERT 1"));
        assertTrue(htmlContent.contains("Skill ADVANCED 1"));
        assertTrue(htmlContent.contains("Skill INTERMEDIATE 1"));
        assertTrue(htmlContent.contains("Skill BEGINNER 1"));
        assertTrue(htmlContent.contains("Microsoft"));
        assertTrue(htmlContent.contains("Google"));
        assertTrue(htmlContent.contains("TestName UNIVERSITY1"));
        assertTrue(htmlContent.contains("TestName COLLEGE1"));
        assertTrue(htmlContent.contains("TestName HIGH_SCHOOL1"));
        assertTrue(htmlContent.contains("TestName ELEMENTARY_SCHOOL1"));
        assertTrue(htmlContent.contains("TestName OTHER1"));
        assertTrue(htmlContent.contains("Language Test Name 1"));
        assertTrue(htmlContent.contains("Test Name 1"));
        assertTrue(htmlContent.contains("DE"));
        assertTrue(htmlContent.contains("RU"));
        assertTrue(htmlContent.contains("EN"));
    }
}
