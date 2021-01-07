package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface LongArrayField extends Field, Function<Glob, long[]> {
    default long[] apply(Glob glob) {
        return glob.get(this);
    }
}
