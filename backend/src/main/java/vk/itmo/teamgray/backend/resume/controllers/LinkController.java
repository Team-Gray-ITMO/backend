package vk.itmo.teamgray.backend.resume.controllers;

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
import vk.itmo.teamgray.backend.resume.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.resume.dto.LinkDto;
import vk.itmo.teamgray.backend.resume.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.resume.services.LinkService;

import static vk.itmo.teamgray.backend.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/link")
@RequiredArgsConstructor
@Tag(name = "Link Controller", description = "Controller for managing links")
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Link by ID", description = "Retrieve a link by its ID.", responses = {
        @ApiResponse(description = "Link retrieved successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = LinkDto.class))),
        @ApiResponse(description = "Link not found", responseCode = "404")
    })
    public ResponseEntity<LinkDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(linkService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Link", description = "Create a new link for a resume.", responses = {
        @ApiResponse(description = "Link created successfully", responseCode = "201", content = @Content(schema = @Schema(implementation = LinkDto.class))),
        @ApiResponse(description = "Invalid input data", responseCode = "400")
    })
    public ResponseEntity<LinkDto> create(@RequestBody @Valid LinkCreateDto linkCreateDto) {
        return ResponseEntity
            .status(201)
            .body(linkService.createLink(linkCreateDto));
    }

    @PutMapping
    @Operation(summary = "Update an existing Link", description = "Update the details of an existing link.", responses = {
        @ApiResponse(description = "Link updated successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = LinkDto.class))),
        @ApiResponse(description = "Link not found", responseCode = "404"),
        @ApiResponse(description = "Invalid input data", responseCode = "400")
    })
    public ResponseEntity<LinkDto> update(@RequestBody @Valid LinkUpdateDto linkUpdateDto) {
        return ResponseEntity.ok(linkService.updateLink(linkUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Link by ID", description = "Delete a link by its ID.", responses = {
        @ApiResponse(description = "Link deleted successfully", responseCode = "204"),
        @ApiResponse(description = "Link not found", responseCode = "404")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        linkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
