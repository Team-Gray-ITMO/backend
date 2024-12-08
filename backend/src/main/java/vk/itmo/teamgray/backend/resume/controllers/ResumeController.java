package vk.itmo.teamgray.backend.resume.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.template.services.TemplateMergeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;
    private final TemplateMergeService templateMergeService;

    @GetMapping("/{resumeId}/html")
    public ResponseEntity<ByteArrayResource> getHtml(@PathVariable Long resumeId) {
        byte[] htmlAsArray = templateMergeService.mergeTemplateToHtml(
                resumeService.findById(resumeId)
        );
        ByteArrayResource response = new ByteArrayResource(htmlAsArray);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resumeId}/docx")
    public ResponseEntity<ByteArrayResource> getDocx(@PathVariable Long resumeId) {
        byte[] htmlAsArray = resumeExportService.extractDocx(
                resumeId
        );
        ByteArrayResource response = new ByteArrayResource(htmlAsArray);
        return ResponseEntity.ok(response);
    }
}
