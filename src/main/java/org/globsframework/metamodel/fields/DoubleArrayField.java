package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface DoubleArrayField extends Field, Function<Glob, double[]> {
    default double[] apply(Glob glob) {
        return glob.get(this);
    }
}
