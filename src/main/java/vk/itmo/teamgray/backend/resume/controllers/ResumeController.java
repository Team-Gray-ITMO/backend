package vk.itmo.teamgray.backend.resume.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.itmo.teamgray.backend.file.format.DocxFormat;
import vk.itmo.teamgray.backend.file.format.HtmlFormat;
import vk.itmo.teamgray.backend.file.format.PdfFormat;
import vk.itmo.teamgray.backend.file.format.PngFormat;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.services.ResumeExportService;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/resume")
@RequiredArgsConstructor
@Tag(name = "Resume", description = "CRUD and other operations for resumes")
public class ResumeController {
    private final ResumeExportService resumeExportService;
    private final ResumeService resumeService;

    @GetMapping
    @Operation(
        summary = "Get all Resumes",
        description = "Retrieve all resumes.",
        responses = @ApiResponse(description = "Resumes retrieved successfully", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResumeDto.class))))
    )
    public ResponseEntity<List<ResumeDto>> findAll() {
        return ResponseEntity.ok(resumeService.findAll());
    }

    @GetMapping("/{resumeId}")
    @Operation(
        summary = "Get Resume by ID", description = "Retrieve a resume by its ID.",
        responses = @ApiResponse(description = "Resume retrieved successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = ResumeDto.class)))
    )
    public ResponseEntity<ResumeDto> getById(@PathVariable Long resumeId) {
        return ResponseEntity.ok(resumeService.findById(resumeId));
    }

    @PostMapping
    @Operation(
        summary = "Create a new Resume", description = "Create a new resume for a user.",
        responses = @ApiResponse(description = "Resume created successfully", responseCode = "201", content = @Content(schema = @Schema(implementation = ResumeDto.class)))
    )
    public ResponseEntity<ResumeDto> create(@RequestBody @Valid ResumeCreateDto resumeCreateDto) {
        return ResponseEntity
            .status(201)
            .body(resumeService.createResume(resumeCreateDto));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing Resume", description = "Update the details of an existing resume.",
        responses = @ApiResponse(description = "Resume updated successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = ResumeDto.class)))
    )
    public ResponseEntity<ResumeDto> update(@RequestBody @Valid ResumeUpdateDto resumeUpdateDto) {
        return ResponseEntity.ok(resumeService.updateResume(resumeUpdateDto));
    }

    @DeleteMapping("/{resumeId}")
    @Operation(
        summary = "Delete Resume by ID", description = "Delete a resume by its ID.",
        responses = @ApiResponse(description = "Resume deleted successfully", responseCode = "204")
    )
    public ResponseEntity<Void> delete(@PathVariable Long resumeId) {
        resumeService.deleteById(resumeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{resumeId}/html")
    @Operation(
        summary = "Get Resume as HTML",
        responses = @ApiResponse(description = "HTML retrieved successfully", responseCode = "200", content = @Content(mediaType = HtmlFormat.MIME_TYPE_OCTET_STREAM))
    )
    public ResponseEntity<ByteArrayResource> getHtml(@PathVariable Long resumeId) {
        byte[] htmlAsArray = resumeExportService.getResumeAsHtml(resumeId);

        ByteArrayResource response = new ByteArrayResource(htmlAsArray);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resumeId}/pdf")
    @Operation(
        summary = "Get Resume as PDF",
        responses = @ApiResponse(description = "PDF retrieved successfully", responseCode = "200", content = @Content(mediaType = PdfFormat.MIME_TYPE))
    )
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable Long resumeId) {
        byte[] pdfAsArray = resumeExportService.getResumeAsPdf(resumeId);

        ByteArrayResource resource = new ByteArrayResource(pdfAsArray);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_" + resumeId + PdfFormat.EXTENSION);
        headers.add(HttpHeaders.CONTENT_TYPE, PdfFormat.MIME_TYPE);

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headers)
            .body(resource);
    }

    @GetMapping("/{resumeId}/docx")
    @Operation(
        summary = "Get Resume as DOCX",
        responses = @ApiResponse(description = "DOCX retrieved successfully", responseCode = "200", content = @Content(mediaType = DocxFormat.MIME_TYPE))
    )
    public void getDocx(@PathVariable Long resumeId, HttpServletResponse response) throws IOException {
        byte[] docxAsArray = resumeExportService.getResumeAsDocx(resumeId);

        response.setContentType(DocxFormat.MIME_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename=resume_" + resumeId + DocxFormat.EXTENSION);
        response.getOutputStream().write(docxAsArray);
    }

    @GetMapping("/{resumeId}/png")
    @Operation(
        summary = "Get Resume as PNG",
        responses = @ApiResponse(description = "PNG retrieved successfully", responseCode = "200", content = @Content(mediaType = PngFormat.MIME_TYPE))
    )
    public void getPng(@PathVariable Long resumeId, HttpServletResponse response) throws IOException {
        byte[] pngByteArray = resumeExportService.getResumeAsPng(resumeId);

        response.setContentType(PngFormat.MIME_TYPE);
        response.setHeader("Content-Disposition", "attachment; filename=resume_" + resumeId + PngFormat.EXTENSION);
        response.getOutputStream().write(pngByteArray);
    }
}
