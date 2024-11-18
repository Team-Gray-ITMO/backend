package vk.itmo.teamgray.backend.common.exceptions;

public class ModelNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "ERROR.MODEL_NOT_FOUND";
    }
}
