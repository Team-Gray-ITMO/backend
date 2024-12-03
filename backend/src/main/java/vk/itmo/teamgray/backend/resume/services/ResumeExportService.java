package vk.itmo.teamgray.backend.resume.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.template.services.TemplateMergeService;

@Service
@RequiredArgsConstructor
public class ResumeExportService {
    private final ResumeService resumeService;
    private final TemplateMergeService templateMergeService;

    public byte[] extractHtml(Long resumeId){
        Resume resume = resumeService.findById(resumeId);
        return templateMergeService.mergeTemplateToHtml(resume, resume.getTemplate());
    }
}
