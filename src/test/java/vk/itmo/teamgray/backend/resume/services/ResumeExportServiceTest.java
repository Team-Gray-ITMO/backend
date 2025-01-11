package vk.itmo.teamgray.backend.resume.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import static org.assertj.core.api.Assertions.assertThat;

class ResumeExportServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeExportServiceTest.class);

    @Autowired
    private ResumeExportService resumeExportService;

    private void writeToFile(FileFormat format, byte[] fileBytes) throws IOException {
        Path outputDir = Paths.get("build", "test-output", "merged-template");

        File buildFolder = outputDir.toFile();
        if (!buildFolder.exists()) {
            buildFolder.mkdirs();
        }
        File outputFile = new File(buildFolder, "resume" + format.getExtension());
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(fileBytes);
            log.info("File written successfully: {}", outputFile.getAbsolutePath());
        }
        assertThat(outputFile.exists()).isTrue();
        assertThat(outputFile.length()).isGreaterThan(0);
    }

    @Test
    void testHtmlResumeExport() throws IOException {
        var resume = resumeGenerator.generateResumes(1, sampleTemplates.getFirst().getId()).getFirst();

        byte[] htmlResume = resumeExportService.getResumeAsHtml(resume.getId());
        assertThat(htmlResume).isNotNull();

        writeToFile(new HtmlFormat(), htmlResume);
    }

    @Test
    void testPdfResumeExport() throws IOException {
        var resume = resumeGenerator.generateResumes(1, sampleTemplates.getFirst().getId()).getFirst();

        byte[] pdfResume = resumeExportService.getResumeAsPdf(resume.getId());
        assertThat(pdfResume).isNotNull();

        writeToFile(new PdfFormat(), pdfResume);
    }

    @Test
    void testPngResumeExport() throws IOException {
        var resume = resumeGenerator.generateResumes(1, sampleTemplates.getFirst().getId()).getFirst();

        byte[] pngResume = resumeExportService.getResumeAsPng(resume.getId());
        assertThat(pngResume).isNotNull();

        writeToFile(new PngFormat(), pngResume);
    }

    @Test
    void testDocxResumeExport() throws IOException {
        var resume = resumeGenerator.generateResumes(1, sampleTemplates.getFirst().getId()).getFirst();

        byte[] docxResume = resumeExportService.getResumeAsDocx(resume.getId());
        assertThat(docxResume).isNotNull();

        writeToFile(new DocxFormat(), docxResume);
    }
}
