package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface BooleanField extends Field, Function<Glob, Boolean> {
    default Boolean apply(Glob glob) {
        return glob.get(this);
    }
}
