package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface LongField extends Field, Function<FieldValuesAccessor, Long> {
    default Long apply(FieldValuesAccessor data) {
        return data.get(this);
    }
}
