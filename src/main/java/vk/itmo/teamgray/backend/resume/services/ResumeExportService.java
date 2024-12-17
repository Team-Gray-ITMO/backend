package vk.itmo.teamgray.backend.resume.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.file.FileConversionService;
import vk.itmo.teamgray.backend.template.merge.services.TemplateMergeService;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeExportService {
    private final ResumeService resumeService;
    private final TemplateMergeService templateMergeService;
    private final FileConversionService fileConversionService;

    public byte[] getResumeAsHtml(Long resumeId) {
        return templateMergeService.mergeTemplateToHtml(resumeService.findById(resumeId));
    }

    public byte[] getResumeAsPdf(Long resumeId) {
        return fileConversionService.convertHtmlToPdf(getResumeAsHtml(resumeId));
    }

    public byte[] getResumeAsDocx(Long resumeId) {
        return fileConversionService.convertHtmlToDocx(getResumeAsHtml(resumeId));
    }

    public byte[] getResumeAsPng(Long resumeId) {
        return fileConversionService.convertPdfToPng(getResumeAsPdf(resumeId));
    }
}
