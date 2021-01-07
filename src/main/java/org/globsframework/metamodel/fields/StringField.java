package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface StringField extends Field, Function<Glob, String> {
    default String apply(Glob glob) {
        return glob.get(this);
    }
}
