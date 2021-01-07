package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface BooleanArrayField extends Field, Function<Glob, boolean[]> {
    default boolean[] apply(Glob glob) {
        return glob.get(this);
    }
}
