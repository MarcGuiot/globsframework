package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.util.Collection;
import java.util.function.Function;

public non-sealed interface GlobUnionField extends Field, Function<FieldValuesAccessor, Glob> {
    Collection<GlobType> getTargetTypes();

    GlobType getTargetType(String name);

    default Glob apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }

    /*
        Dangerous
        Use with caution
         */
    void __add__(GlobType type);
}
