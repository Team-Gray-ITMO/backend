package vk.itmo.teamgray.backend.resume.services;

import com.itextpdf.html2pdf.HtmlConverter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.swing.Java2DRenderer;
import vk.itmo.teamgray.backend.file.utils.ZipUtils;
import vk.itmo.teamgray.backend.resume.exceptions.ConversionException;
import vk.itmo.teamgray.backend.resume.generator.ResumeSampleGenerator;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;
import vk.itmo.teamgray.backend.template.services.TemplateService;

import javax.imageio.ImageIO;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeExportService {
    private final ResumeService resumeService;
    private final TemplateService templateService;
    private final ResumeSampleGenerator resumeSampleGenerator;
    private final TemplateMergeService templateMergeService;

    public byte[] extractHtml(Long resumeId) {
        return templateMergeService.mergeTemplateToHtml(resumeService.findById(resumeId));
    }

    public byte[] extractPdf(Long resumeId) {
        try {
            var htmlTemplate = extractHtml(resumeId);
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlTemplate), pdfOutputStream);
            return pdfOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ConversionException("ERROR.CONVERT_TO_PDF: " + e.getMessage());
        }
    }

    public byte[] extractDocx(Long resumeId) {
        byte[] htmlTemplate = extractHtml(resumeId);

        String htmlContent = new String(htmlTemplate, StandardCharsets.UTF_8);

        XWPFDocument document = new XWPFDocument();

        Document htmlDocument = Jsoup.parse(htmlContent);
        Elements paragraphs = htmlDocument.body().select("p, h1, h2, h3, h4, h5, h6");

        for (Element paragraph : paragraphs) {
            XWPFParagraph docParagraph = document.createParagraph();
            XWPFRun run = docParagraph.createRun();
            run.setText(paragraph.text());

            switch (paragraph.tagName()) {
                case "h1" -> run.setBold(true);
                case "h2" -> {
                    run.setBold(true);
                    run.setFontSize(18);
                }
                case "h3" -> run.setFontSize(16);
            }
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            document.write(outputStream);
            document.close();
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new ConversionException("ERROR.CONVERT_TO_DOCX: " + ex.getMessage());
        }
    }

    public byte[] extractImage(Long resumeId) {
        var htmlTemplate = extractHtml(resumeId);
        return htmlToPngBytes(htmlTemplate);
    }

    public byte[] extractTemplateImage(Long templateId) {
        var template = templateMergeService.getTemplateAndFill(templateId);
        var mapOfFiles = ZipUtils.extractZipContents(template.getFile());
        return htmlToPngBytes(mapOfFiles.get(TemplateMergeService.INDEX_HTML_FILENAME));
    }

    private byte[] htmlToPngBytes(byte[] htmlTemplate) {
        String htmlContent = new String(htmlTemplate, StandardCharsets.UTF_8);
        String cleanHtml = cleanHtml(htmlContent);

        try {
            Java2DRenderer renderer = new Java2DRenderer(cleanHtml, 800, 600);
            BufferedImage image = renderer.getImage();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ConversionException("ERROR.CONVERT_TO_IMAGE: " + e.getMessage());
        }
    }

    private String cleanHtml(String html) {
        Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return doc.html();
    }
}
