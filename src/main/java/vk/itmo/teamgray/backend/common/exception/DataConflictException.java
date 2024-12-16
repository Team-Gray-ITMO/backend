package vk.itmo.teamgray.backend.common.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(final String message) {
        super(message);
    }

    public static DataConflictException unique(Class<?> type, String fieldName, String fieldValue, long id) {
        return new DataConflictException(
            type.getSimpleName() + " already exists with " + fieldName + " value of " + fieldValue + ". [id=" + id + "]"
        );
    }
}
