package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.Collection;
import java.util.function.Function;

public interface GlobArrayUnionField extends Field, Function<Glob, Glob[]> {
    Collection<GlobType> getTypes();

    GlobType get(String name);

    default Glob[] apply(Glob glob) {
        return glob.get(this);
    }

    /*
        Dangerous
        Use with caution
         */
    void __add__(GlobType type);
}
