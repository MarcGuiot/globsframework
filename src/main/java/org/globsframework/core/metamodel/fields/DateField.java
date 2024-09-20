package org.globsframework.core.metamodel.fields;

import org.globsframework.core.model.FieldValuesAccessor;

import java.time.LocalDate;
import java.util.function.Function;

public non-sealed interface DateField extends Field, Function<FieldValuesAccessor, LocalDate> {
    default LocalDate apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
