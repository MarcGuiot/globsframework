package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.impl.DefaultAnnotations;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.Ref;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface Annotations {
    Annotations EMPTY = new DefaultAnnotations();

    Stream<Glob> streamAnnotations();

    Stream<Glob> streamAnnotations(GlobType type);

    boolean hasAnnotation(Key key);

    Collection<Glob> getAnnotations();

    Glob getAnnotation(Key key);

    Glob findAnnotation(Key key);

    default <T> T getValueOrDefault(Key key, Field field, T defaultValue) {
        Glob annotation = findAnnotation(key);
        if (annotation != null) {
            return (T) annotation.getValue(field);
        }
        return defaultValue;
    }

    default Optional<Glob> findOptAnnotation(Key key) {
        return Optional.ofNullable(findAnnotation(key));
    }

    default boolean findAnnotation(Key key, Ref<Glob> result) {
        result.set(findAnnotation(key));
        return result.get() != null;
    }

    default Glob findUniqueAnnotation(GlobType globType) {
        Optional<Glob> first = streamAnnotations(globType).findFirst();
        return first.isPresent() ? first.get() : null;
    }
}
