package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public interface IntegerArrayField extends Field, Function<FieldValuesAccessor, int[]> {
    default int[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
