package vk.itmo.teamgray.backend.file.exception;

public class FileStorageServiceException extends RuntimeException {
    public FileStorageServiceException(String message) {
        super(message);
    }

    public FileStorageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
