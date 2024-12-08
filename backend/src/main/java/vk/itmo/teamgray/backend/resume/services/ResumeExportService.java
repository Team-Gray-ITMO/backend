package vk.itmo.teamgray.backend.resume.services;

import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.resume.exceptions.ConvertionException;
import vk.itmo.teamgray.backend.template.services.TemplateMergeService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeExportService {
    private final ResumeService resumeService;
    private final TemplateMergeService templateMergeService;

    public byte[] extractHtml(Long resumeId) {
        Resume resume = resumeService.findById(resumeId);
        return templateMergeService.mergeTemplateToHtml(resume);
    }

    public byte[] extractPdf(Long resumeId) {
        try {
            Resume resume = resumeService.findById(resumeId);
            var htmlTemplate = templateMergeService.mergeTemplateToHtml(resume);
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlTemplate), pdfOutputStream);
            return pdfOutputStream.toByteArray();
        }
        catch (IOException e){
            throw new ConvertionException("ERROR.CONVERT_TO_PDF: " + e.getMessage());
        }
    }

    public byte[] extractDocx(Long resumeId) {
        Resume resume = resumeService.findById(resumeId);
        return templateMergeService.mergeTemplateToHtml(resume);
    }
}
