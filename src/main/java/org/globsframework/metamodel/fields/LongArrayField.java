package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface LongArrayField extends Field, Function<FieldValuesAccessor, long[]> {
    default long[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
