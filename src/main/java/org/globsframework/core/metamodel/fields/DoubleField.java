package org.globsframework.core.metamodel.fields;

import org.globsframework.core.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface DoubleField extends Field, Function<FieldValuesAccessor, Double> {
    default Double apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
