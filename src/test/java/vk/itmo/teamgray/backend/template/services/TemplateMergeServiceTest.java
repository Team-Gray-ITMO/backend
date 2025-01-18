package vk.itmo.teamgray.backend.template.services;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateMergeServiceTest extends TestBase {
    @Autowired
    private TemplateMergeService templateMergeService;

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
