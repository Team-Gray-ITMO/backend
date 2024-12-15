package vk.itmo.teamgray.backend.common.exception.handler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vk.itmo.teamgray.backend.common.exception.DataConflictException;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.exception.handler.response.BadRequestErrorResponse;
import vk.itmo.teamgray.backend.common.exception.handler.response.ConflictErrorResponse;
import vk.itmo.teamgray.backend.common.exception.handler.response.InternalServerErrorResponse;
import vk.itmo.teamgray.backend.common.exception.handler.response.NotFoundErrorResponse;
import vk.itmo.teamgray.backend.common.exception.handler.response.UnauthorizedErrorResponse;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        @ApiResponse(
            responseCode = BadRequestErrorResponse.CODE,
            description = "Bad Request - Invalid argument",
            content = @Content(schema = @Schema(implementation = BadRequestErrorResponse.class))
        )
    )
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException exception) {
        logger.warn(exception.getMessage(), exception);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new BadRequestErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponses(
        @ApiResponse(
            responseCode = UnauthorizedErrorResponse.CODE,
            description = "Unauthorized - Access Denied",
            content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponse.class))
        )
    )
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {
        logger.warn(exception.getMessage(), exception);

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new UnauthorizedErrorResponse(((Exception)exception).getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(
        @ApiResponse(
            responseCode = NotFoundErrorResponse.CODE,
            description = "Not Found - Data not found",
            content = @Content(schema = @Schema(implementation = NotFoundErrorResponse.class))
        )
    )
    public ResponseEntity<Object> handleNotFoundException(final DataNotFoundException exception) {
        logger.warn(exception.getMessage(), exception);

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new NotFoundErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(DataConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponses(
        @ApiResponse(
            responseCode = ConflictErrorResponse.CODE,
            description = "Conflict - Data conflict",
            content = @Content(schema = @Schema(implementation = ConflictErrorResponse.class))
        )
    )
    public ResponseEntity<Object> handleDataConflictException(final DataConflictException exception) {
        logger.warn(exception.getMessage(), exception);

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ConflictErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(
        @ApiResponse(
            responseCode = InternalServerErrorResponse.CODE,
            description = "Internal Server Error - Unknown error occurred",
            content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class))
        )
    )
    public ResponseEntity<Object> handleAllUncaughtException(final Exception exception) {
        logger.error("Unknown error occurred", exception);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new InternalServerErrorResponse(exception.getMessage()));
    }

    @Override
    @ApiResponses(
        @ApiResponse(
            responseCode = BadRequestErrorResponse.CODE,
            description = "Bad Request - Invalid argument",
            content = @Content(schema = @Schema(implementation = BadRequestErrorResponse.class))
        )
    )
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        String errorMessage = validationErrors.values().toString();

        return ResponseEntity
            .status(status)
            .body(new BadRequestErrorResponse(errorMessage));
    }
}
