package vk.itmo.teamgray.backend.cetification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationUpdateDto;
import vk.itmo.teamgray.backend.cetification.services.CertificationService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/certification")
@RequiredArgsConstructor
@Tag(name = "Certification", description = "CRUD operations for certifications")
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get certification by ID",
        responses = @ApiResponse(description = "Certification found", responseCode = "200", content = @Content(schema = @Schema(implementation = CertificationDto.class)))
    )
    public ResponseEntity<CertificationDto> getCertificationById(@PathVariable Long id) {
        return ResponseEntity.ok(certificationService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new certification",
        responses = @ApiResponse(description = "Certification created", responseCode = "201", content = @Content(schema = @Schema(implementation = CertificationDto.class)))
    )
    public ResponseEntity<CertificationDto> createCertification(@RequestBody @Validated CertificationCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(certificationService.createCertification(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing certification",
        responses = @ApiResponse(description = "Certification updated", responseCode = "200", content = @Content(schema = @Schema(implementation = CertificationDto.class)))
    )
    public ResponseEntity<CertificationDto> updateCertification(@RequestBody @Validated CertificationUpdateDto data) {
        return ResponseEntity.ok(certificationService.updateCertification(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a certification by ID",
        responses = @ApiResponse(description = "Certification deleted", responseCode = "204")
    )
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
