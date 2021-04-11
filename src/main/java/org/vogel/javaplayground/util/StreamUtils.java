package org.vogel.javaplayground.util;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class StreamUtils {
    public static <T, U> Predicate<T> isEqual(Function<T, U> propertyGetter, U value) {
        return (T obj) -> Objects.equals(propertyGetter.apply(obj), value);
    }
}
