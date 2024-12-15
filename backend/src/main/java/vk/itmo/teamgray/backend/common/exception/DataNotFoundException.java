package vk.itmo.teamgray.backend.common.exception;

import vk.itmo.teamgray.backend.common.entities.BaseEntity;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(final String message) {
        super(message);
    }

    public static DataNotFoundException entity(Class<? extends BaseEntity> entityClass, long id) {
        return new DataNotFoundException("Entity " + entityClass.getSimpleName() + " was not found. [id=" + id + "]");
    }
}
