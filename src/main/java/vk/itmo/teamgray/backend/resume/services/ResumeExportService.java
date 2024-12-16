package vk.itmo.teamgray.backend.resume.services;

import com.itextpdf.html2pdf.HtmlConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import vk.itmo.teamgray.backend.resume.exceptions.ConversionException;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeExportService {
    private final ResumeService resumeService;
    private final TemplateMergeService templateMergeService;

    public byte[] extractHtml(Long resumeId) {
        return templateMergeService.mergeTemplateToHtml(resumeService.findById(resumeId));
    }

    public byte[] extractPdf(Long resumeId) {
        try {
            var htmlTemplate = templateMergeService.mergeTemplateToHtml(resumeService.findById(resumeId));
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlTemplate), pdfOutputStream);
            return pdfOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ConversionException("ERROR.CONVERT_TO_PDF: " + e.getMessage());
        }
    }

    public byte[] extractDocx(Long resumeId) {
        byte[] htmlTemplate = templateMergeService.mergeTemplateToHtml(resumeService.findById(resumeId));

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
}