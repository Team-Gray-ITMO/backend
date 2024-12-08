package vk.itmo.teamgray.backend.resume.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vk.itmo.teamgray.backend.resume.services.ResumeExportService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeExportService resumeExportService;

    @GetMapping("/{resumeId}/html")
    public ResponseEntity<ByteArrayResource> getHtml(@PathVariable Long resumeId) {
        byte[] htmlAsArray = resumeExportService.extractHtml(
                resumeId
        );
        ByteArrayResource response = new ByteArrayResource(htmlAsArray);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resumeId}/pdf")
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable Long resumeId) {
        byte[] pdfAsArray = resumeExportService.extractPdf(
                resumeId
        );
        ByteArrayResource resource = new ByteArrayResource(pdfAsArray);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_" + resumeId + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(resource);
    }
}
