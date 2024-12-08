package vk.itmo.teamgray.backend.resume.controllers;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.services.ResumeExportService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

import static vk.itmo.teamgray.backend.config.ApplicationConfiguration.API_VER;

@Controller
@RequiredArgsConstructor
@RequestMapping(API_VER + "/resume")
public class ResumeController {
    private final ResumeExportService resumeExportService;
    private final ResumeService resumeService;

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

    @GetMapping("/{resumeId}/docx")
    public void getDocx(@PathVariable Long resumeId, HttpServletResponse response) throws IOException {
        byte[] docxAsArray = resumeExportService.extractDocx(
            resumeId
        );

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=resume_" + resumeId + ".docx");
        response.getOutputStream().write(docxAsArray);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResumeDto>> getMyResumes() {
        return ResponseEntity.ok(resumeService.findAll());
    }
}
