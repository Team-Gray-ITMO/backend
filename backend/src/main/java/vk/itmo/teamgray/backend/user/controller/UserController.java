package vk.itmo.teamgray.backend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;
import vk.itmo.teamgray.backend.user.service.UserService;

import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RestController
@RequestMapping(API_VER + "/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to user management")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user by ID", responses = {
        @ApiResponse(description = "Successfully retrieved user", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(description = "User not found", responseCode = "404")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Create a new user", responses = {
        @ApiResponse(description = "Successfully created user", responseCode = "201", content = @Content(schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(description = "Invalid input", responseCode = "400")
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity
            .status(201)
            .body(userService.createUser(userCreateDto));
    }
}
