package vk.itmo.teamgray.backend.educationinstitution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionUpdateDto;
import vk.itmo.teamgray.backend.educationinstitution.services.EducationInstitutionService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/education-institution")
@RequiredArgsConstructor
@Tag(name = "Education Institution", description = "CRUD operations for education institutions")
public class EducationInstitutionController {

    private final EducationInstitutionService educationInstitutionService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get education institution by ID",
        responses = {
            @ApiResponse(description = "Institution found", responseCode = "200", content = @Content(schema = @Schema(implementation = EducationInstitutionDto.class))),
            @ApiResponse(description = "Institution not found", responseCode = "404")
        }
    )
    public ResponseEntity<EducationInstitutionDto> getInstitutionById(@PathVariable Long id) {
        return ResponseEntity.ok(educationInstitutionService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new education institution",
        responses = {
            @ApiResponse(description = "Institution created", responseCode = "201", content = @Content(schema = @Schema(implementation = EducationInstitutionDto.class))),
            @ApiResponse(description = "Invalid input", responseCode = "400")
        }
    )
    public ResponseEntity<EducationInstitutionDto> createInstitution(@RequestBody @Valid EducationInstitutionCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(educationInstitutionService.createEducationInstitution(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing education institution",
        responses = {
            @ApiResponse(description = "Institution updated", responseCode = "200", content = @Content(schema = @Schema(implementation = EducationInstitutionDto.class))),
            @ApiResponse(description = "Invalid input or institution not found", responseCode = "400")
        }
    )
    public ResponseEntity<EducationInstitutionDto> updateInstitution(@RequestBody @Valid EducationInstitutionUpdateDto data) {
        return ResponseEntity.ok(educationInstitutionService.updateEducationInstitution(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an education institution by ID",
        responses = {
            @ApiResponse(description = "Institution deleted", responseCode = "204"),
            @ApiResponse(description = "Institution not found", responseCode = "404")
        }
    )
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        educationInstitutionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
