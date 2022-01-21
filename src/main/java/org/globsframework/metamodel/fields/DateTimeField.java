package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;

import java.time.ZonedDateTime;
import java.util.function.Function;

public interface DateTimeField extends Field, Function<FieldValuesAccessor, ZonedDateTime> {
    default ZonedDateTime apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
