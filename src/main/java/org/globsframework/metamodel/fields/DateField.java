package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.time.LocalDate;
import java.util.function.Function;

public non-sealed interface DateField extends Field, Function<FieldValuesAccessor, LocalDate> {
    default LocalDate apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
