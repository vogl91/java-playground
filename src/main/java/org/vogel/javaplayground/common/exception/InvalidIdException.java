package org.vogel.javaplayground.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * throw when a DTO has an id which is not in the database.
 * This is a client error.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(final Class<?> clazz, final Long id) {
        super(String.format("Id '%d' of entity '%s' is invalid because it is not linked to parent entity", id,
                            clazz.getSimpleName()));
    }
}
