package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.time.LocalDate;
import java.util.function.Function;

public interface DateField extends Field, Function<FieldValuesAccessor, LocalDate> {
    default LocalDate apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
