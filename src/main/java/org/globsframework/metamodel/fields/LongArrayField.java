package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public interface LongArrayField extends Field, Function<FieldValuesAccessor, long[]> {
    default long[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
