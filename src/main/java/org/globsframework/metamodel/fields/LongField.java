package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface LongField extends Field, Function<Glob, Long> {
    default Long apply(Glob glob) {
        return glob.get(this);
    }
}
