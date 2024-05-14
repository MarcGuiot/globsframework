package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface BooleanArrayField extends Field, Function<FieldValuesAccessor, boolean[]> {
    default boolean[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
