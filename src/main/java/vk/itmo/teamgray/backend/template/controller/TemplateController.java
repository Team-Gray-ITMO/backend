package vk.itmo.teamgray.backend.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateImageDto;
import vk.itmo.teamgray.backend.template.dto.TemplateUpdateDto;
import vk.itmo.teamgray.backend.template.services.TemplateImageService;
import vk.itmo.teamgray.backend.template.services.TemplateService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/template")
@RequiredArgsConstructor
@Tag(name = "Template", description = "CRUD and other operations for templates")
public class TemplateController {
    private final TemplateService templateService;

    private final TemplateImageService templateImageService;

    @GetMapping
    @Operation(
        summary = "Get all Templates",
        description = "Retrieve all templates.",
        responses = @ApiResponse(description = "Templates retrieved successfully", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TemplateDto.class))))
    )
    public ResponseEntity<List<TemplateDto>> getAllTemplates() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @GetMapping("/image")
    @Operation(
        summary = "Get all Templates With Images of Filled Templates",
        description = "Retrieve all templates with images.",
        responses = @ApiResponse(description = "Templates retrieved successfully", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TemplateImageDto.class))))
    )
    public ResponseEntity<List<TemplateImageDto>> getAllTemplatesWithImages() {
        return ResponseEntity.ok(templateImageService.generateImagesForAllTemplates());
    }

    @Operation(summary = "Get a template with image of Filled Template by ID", responses = {
        @ApiResponse(description = "Template retrieved successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = TemplateImageDto.class))),
        @ApiResponse(description = "Template not found", responseCode = "404")
    })
    @GetMapping("/image/{id}")
    public ResponseEntity<TemplateImageDto> getTemplateWithImage(@PathVariable Long id) {
        return ResponseEntity.ok(templateImageService.generateImageForTemplate(id));
    }

    @Operation(summary = "Get a template by ID", responses = {
        @ApiResponse(description = "Template retrieved successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = TemplateDto.class))),
        @ApiResponse(description = "Template not found", responseCode = "404")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TemplateDto> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.findById(id));
    }

    @Operation(summary = "Create a new template", responses = {
        @ApiResponse(description = "Template created successfully", responseCode = "201", content = @Content(schema = @Schema(implementation = TemplateDto.class))),
        @ApiResponse(description = "Invalid input data", responseCode = "400")
    })
    @PostMapping
    public ResponseEntity<TemplateDto> createTemplate(@Validated @RequestBody TemplateCreateDto dto) {
        return ResponseEntity
            .status(201)
            .body(templateService.createTemplate(dto));
    }

    @Operation(summary = "Update an existing template", responses = {
        @ApiResponse(description = "Template updated successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = TemplateDto.class))),
        @ApiResponse(description = "Template not found", responseCode = "404"),
        @ApiResponse(description = "Invalid input data", responseCode = "400")
    })
    @PutMapping
    public ResponseEntity<TemplateDto> updateTemplate(@Validated @RequestBody TemplateUpdateDto dto) {
        return ResponseEntity.ok(templateService.updateTemplate(dto));
    }

    @Operation(summary = "Delete a template by ID", responses = {
        @ApiResponse(description = "Template deleted successfully", responseCode = "200"),
        @ApiResponse(description = "Template not found", responseCode = "404")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
