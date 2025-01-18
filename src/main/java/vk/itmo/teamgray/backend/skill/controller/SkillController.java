package vk.itmo.teamgray.backend.skill.controller;

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
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.skill.dto.SkillUpdateDto;
import vk.itmo.teamgray.backend.skill.services.SkillService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/skill")
@RequiredArgsConstructor
@Tag(name = "Skill", description = "CRUD operations for skills")
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a skill by ID",
        responses = @ApiResponse(description = "Skill found", responseCode = "200", content = @Content(schema = @Schema(implementation = SkillDto.class)))
    )
    public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new skill",
        responses = @ApiResponse(description = "Skill created", responseCode = "201", content = @Content(schema = @Schema(implementation = SkillDto.class)))
    )
    public ResponseEntity<SkillDto> createSkill(@RequestBody @Validated SkillCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(skillService.createSkill(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing skill",
        responses = {
            @ApiResponse(description = "Skill updated", responseCode = "200", content = @Content(schema = @Schema(implementation = SkillDto.class))),
            @ApiResponse(description = "Invalid input or skill not found", responseCode = "400")
        }
    )
    public ResponseEntity<SkillDto> updateSkill(@RequestBody @Validated SkillUpdateDto data) {
        return ResponseEntity.ok(skillService.updateSkill(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a skill by ID",
        responses = {
            @ApiResponse(description = "Skill deleted", responseCode = "204"),
            @ApiResponse(description = "Skill not found", responseCode = "404")
        }
    )
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
