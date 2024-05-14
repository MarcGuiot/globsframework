package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface LongField extends Field, Function<FieldValuesAccessor, Long> {
    default Long apply(FieldValuesAccessor data) {
        return data.get(this);
    }
}
