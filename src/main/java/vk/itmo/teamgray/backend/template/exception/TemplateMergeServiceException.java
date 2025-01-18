package vk.itmo.teamgray.backend.template.exception;

public class TemplateMergeServiceException extends RuntimeException {
    public TemplateMergeServiceException(String message) {
        super(message);
    }

    public TemplateMergeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
