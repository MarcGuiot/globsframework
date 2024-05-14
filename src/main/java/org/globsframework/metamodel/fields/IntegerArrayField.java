package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface IntegerArrayField extends Field, Function<FieldValuesAccessor, int[]> {
    default int[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
