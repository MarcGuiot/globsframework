package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface IntegerField extends Field, Function<FieldValuesAccessor, Integer> {
    default Integer apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
