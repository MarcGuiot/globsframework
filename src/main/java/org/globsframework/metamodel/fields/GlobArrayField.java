package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface GlobArrayField extends Field, Function<Glob, Glob[]> {
    GlobType getTargetType();

    default Glob[] apply(Glob glob) {
        return glob.get(this);
    }
}
