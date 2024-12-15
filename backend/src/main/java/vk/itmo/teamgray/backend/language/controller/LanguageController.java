package vk.itmo.teamgray.backend.language.controller;

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
import vk.itmo.teamgray.backend.language.dto.LanguageCreateDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.language.dto.LanguageUpdateDto;
import vk.itmo.teamgray.backend.language.services.LanguageService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/language")
@RequiredArgsConstructor
@Tag(name = "Language", description = "CRUD operations for language records")
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a language record by ID",
        responses = @ApiResponse(description = "Language record found", responseCode = "200", content = @Content(schema = @Schema(implementation = LanguageDto.class)))
    )
    public ResponseEntity<LanguageDto> getLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new language record",
        responses = @ApiResponse(description = "Language record created", responseCode = "201", content = @Content(schema = @Schema(implementation = LanguageDto.class)))
    )
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody @Valid LanguageCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(languageService.createLanguage(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing language record",
        responses = @ApiResponse(description = "Language record updated", responseCode = "200", content = @Content(schema = @Schema(implementation = LanguageDto.class)))
    )
    public ResponseEntity<LanguageDto> updateLanguage(@RequestBody @Valid LanguageUpdateDto data) {
        return ResponseEntity.ok(languageService.updateLanguage(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a language record by ID",
        responses = @ApiResponse(description = "Language record deleted", responseCode = "204")
    )
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
