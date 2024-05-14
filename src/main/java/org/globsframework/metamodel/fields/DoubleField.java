package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface DoubleField extends Field, Function<FieldValuesAccessor, Double> {
    default Double apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
