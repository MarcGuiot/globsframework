package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;
import java.util.function.Predicate;

public interface BooleanField extends Field, Function<Glob, Boolean>, Predicate<Glob> {
    default Boolean apply(Glob glob) {
        return glob.get(this);
    }

    default boolean test(Glob glob) {
        return glob.isTrue(this);
    }
}
