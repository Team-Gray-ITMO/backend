package vk.itmo.teamgray.backend.job.controller;

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
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.dto.JobUpdateDto;
import vk.itmo.teamgray.backend.job.service.JobService;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "CRUD operations for job records")
public class JobController {
    private final JobService jobService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a job record by ID",
        responses = {
            @ApiResponse(description = "Job record found", responseCode = "200", content = @Content(schema = @Schema(implementation = JobDto.class))),
            @ApiResponse(description = "Job record not found", responseCode = "404")
        }
    )
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.findById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create a new job record",
        responses = {
            @ApiResponse(description = "Job record created", responseCode = "201", content = @Content(schema = @Schema(implementation = JobDto.class))),
            @ApiResponse(description = "Invalid input", responseCode = "400")
        }
    )
    public ResponseEntity<JobDto> createJob(@RequestBody @Valid JobCreateDto data) {
        return ResponseEntity
            .status(201)
            .body(jobService.createJob(data));
    }

    @PutMapping
    @Operation(
        summary = "Update an existing job record",
        responses = {
            @ApiResponse(description = "Job record updated", responseCode = "200", content = @Content(schema = @Schema(implementation = JobDto.class))),
            @ApiResponse(description = "Invalid input or record not found", responseCode = "400")
        }
    )
    public ResponseEntity<JobDto> updateJob(@RequestBody @Valid JobUpdateDto data) {
        return ResponseEntity.ok(jobService.updateJob(data));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a job record by ID",
        responses = {
            @ApiResponse(description = "Job record deleted", responseCode = "204"),
            @ApiResponse(description = "Job record not found", responseCode = "404")
        }
    )
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}