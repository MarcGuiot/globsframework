package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface DoubleArrayField extends Field, Function<FieldValuesAccessor, double[]> {
    default double[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
