package vk.itmo.teamgray.backend.template.exception;

public class FileStorageServiceException extends RuntimeException {
    public FileStorageServiceException(String message) {
        super(message);
    }

    public FileStorageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
