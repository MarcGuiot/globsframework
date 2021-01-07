package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface IntegerField extends Field, Function<Glob, Integer> {
    default Integer apply(Glob glob) {
        return glob.get(this);
    }
}
