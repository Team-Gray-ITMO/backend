package vk.itmo.teamgray.backend.template.exception;

public class TemplateImportServiceException extends RuntimeException {
    public TemplateImportServiceException(String message) {
        super(message);
    }

    public TemplateImportServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
