package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface StringField extends Field, Function<FieldValuesAccessor, String> {
    default String apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
