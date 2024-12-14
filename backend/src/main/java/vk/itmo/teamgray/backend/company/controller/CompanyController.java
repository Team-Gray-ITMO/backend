package vk.itmo.teamgray.backend.company.controller;

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
import vk.itmo.teamgray.backend.company.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.company.dto.CompanyDto;
import vk.itmo.teamgray.backend.company.dto.CompanyUpdateDto;
import vk.itmo.teamgray.backend.company.service.CompanyService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/company")
@RequiredArgsConstructor
@Tag(name = "Company", description = "CRUD operations for company records")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get company record by ID",
        responses = @ApiResponse(description = "Company record found", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyDto.class)))
    )
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new company record",
        responses = @ApiResponse(description = "Company record created", responseCode = "201", content = @Content(schema = @Schema(implementation = CompanyDto.class)))
    )
    public ResponseEntity<CompanyDto> createCompany(@RequestBody @Valid CompanyCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(companyService.createCompany(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing company record",
        responses = @ApiResponse(description = "Company record updated", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyDto.class)))
    )
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody @Valid CompanyUpdateDto data) {
        return ResponseEntity.ok(companyService.updateCompany(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a company record by ID",
        responses = @ApiResponse(description = "Company record deleted", responseCode = "204")
    )
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
