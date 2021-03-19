package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface DoubleArrayField extends Field, Function<FieldValuesAccessor, double[]> {
    default double[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
