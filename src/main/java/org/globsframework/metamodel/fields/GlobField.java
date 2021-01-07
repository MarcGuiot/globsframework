package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface GlobField extends Field, Function<Glob, Glob> {
    GlobType getType();

    default Glob apply(Glob glob) {
        return glob.get(this);
    }
}
