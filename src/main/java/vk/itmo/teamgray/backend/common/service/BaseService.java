package vk.itmo.teamgray.backend.common.service;

import java.util.function.Consumer;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

public abstract class BaseService<E extends BaseEntity> {
    protected abstract E findEntityById(Long id);

    //TODO Add equality check optimization later.
    protected static <T> boolean updateIfPresent(
        T value,
        Consumer<T> targetSetter
    ) {
        if (value != null) {
            targetSetter.accept(value);

            return true;
        }

        return false;
    }

    public boolean updateLinkToEntityIfPresent(
        Long id,
        Consumer<E> targetSetter
    ) {
        if (id != null) {
            targetSetter.accept(findEntityById(id));

            return true;
        }

        return false;
    }
}
