package vk.itmo.teamgray.backend.resume.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.file.format.DocxFormat;
import vk.itmo.teamgray.backend.file.format.FileFormat;
import vk.itmo.teamgray.backend.file.format.HtmlFormat;
import vk.itmo.teamgray.backend.file.format.PdfFormat;
import vk.itmo.teamgray.backend.file.format.PngFormat;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;

import static org.assertj.core.api.Assertions.assertThat;

class ResumeExportServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeExportServiceTest.class);

    @Autowired
    private ResumeExportService resumeExportService;

    @Autowired
    private ResumeService resumeService;

    private ResumeDto resume;

    private void writeToFile(String templateName, FileFormat format, byte[] fileBytes) {
        Path outputDir = Paths.get("build", "test-output", "merged-template", templateName);

        File buildFolder = outputDir.toFile();
        if (!buildFolder.exists()) {
            buildFolder.mkdirs();
        }
        File outputFile = new File(buildFolder, templateName + format.getExtension());
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(fileBytes);
            log.info("File written successfully: {}", outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertThat(outputFile.exists()).isTrue();
        assertThat(outputFile.length()).isGreaterThan(0);
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        resume = resumeGenerator.generateResume(null, "");
    }

    @Test
    void testHtmlResumeExport() throws IOException {
        forEachTemplate(templateName -> {
            byte[] htmlResume = resumeExportService.getResumeAsHtml(resume.getId());
            assertThat(htmlResume).isNotNull();

            writeToFile(templateName, new HtmlFormat(), htmlResume);
        });
    }

    @Test
    void testPdfResumeExport() throws IOException {
        forEachTemplate(templateName -> {
            byte[] pdfResume = resumeExportService.getResumeAsPdf(resume.getId());
            assertThat(pdfResume).isNotNull();

            writeToFile(templateName, new PdfFormat(), pdfResume);
        });
    }

    @Test
    void testPngResumeExport() throws IOException {
        forEachTemplate(templateName -> {
            byte[] pngResume = resumeExportService.getResumeAsPng(resume.getId());
            assertThat(pngResume).isNotNull();

            writeToFile(templateName, new PngFormat(), pngResume);
        });
    }

    @Test
    void testDocxResumeExport() throws IOException {
        forEachTemplate(templateName -> {
            byte[] docxResume = resumeExportService.getResumeAsDocx(resume.getId());
            assertThat(docxResume).isNotNull();

            writeToFile(templateName, new DocxFormat(), docxResume);
        });
    }

    private void forEachTemplate(Consumer<String> templateNameConsumer) {
        sampleTemplates.forEach(template -> {
            var updDto = new ResumeUpdateDto();

            updDto.setId(resume.getId());
            updDto.setTemplateId(template.getId());

            resumeService.updateResume(updDto);

            templateNameConsumer.accept(template.getName());
        });
    }
}
