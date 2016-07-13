package se.svennesson.svennelist.util;

import se.svennesson.svennelist.exception.EntityNotFoundException;
import se.svennesson.svennelist.model.AbstractEntity;

public class EntityUtil {

    public static boolean isValidEntity(AbstractEntity entity, Long id) {
        if (null == entity) {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }

        return true;
    }

    public static boolean isValidEntity(AbstractEntity entity, String email) {
        if (null == entity) {
            throw new EntityNotFoundException("Entity not found with email " + email);
        }

        return true;
    }
}
