package vk.itmo.teamgray.backend.common.exception.handler.response;

public interface ErrorResponse {
    String getCode();

    String getMessage();
}
