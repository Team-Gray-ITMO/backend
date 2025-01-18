package vk.itmo.teamgray.backend.education.controller;

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
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.dto.EducationUpdateDto;
import vk.itmo.teamgray.backend.education.services.EducationService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/education")
@RequiredArgsConstructor
@Tag(name = "Education", description = "CRUD operations for education records")
public class EducationController {
    private final EducationService educationService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get education record by ID",
        responses = @ApiResponse(description = "Education record found", responseCode = "200", content = @Content(schema = @Schema(implementation = EducationDto.class)))
    )
    public ResponseEntity<EducationDto> getEducationById(@PathVariable Long id) {
        return ResponseEntity.ok(educationService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new education record",
        responses = @ApiResponse(description = "Education record created", responseCode = "201", content = @Content(schema = @Schema(implementation = EducationDto.class)))
    )
    public ResponseEntity<EducationDto> createEducation(@RequestBody @Validated EducationCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(educationService.createEducation(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing education record",
        responses = @ApiResponse(description = "Education record updated", responseCode = "200", content = @Content(schema = @Schema(implementation = EducationDto.class)))
    )
    public ResponseEntity<EducationDto> updateEducation(@RequestBody @Validated EducationUpdateDto data) {
        return ResponseEntity.ok(educationService.updateEducation(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an education record by ID",
        responses = @ApiResponse(description = "Education record deleted", responseCode = "204")
    )
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
