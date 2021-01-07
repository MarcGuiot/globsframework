package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface DoubleField extends Field, Function<Glob, Double> {
    default Double apply(Glob glob) {
        return glob.get(this);
    }
}
