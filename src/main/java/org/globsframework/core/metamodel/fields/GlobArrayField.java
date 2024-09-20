package org.globsframework.core.metamodel.fields;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.FieldValuesAccessor;
import org.globsframework.core.model.Glob;

import java.util.function.Function;
import java.util.stream.Stream;

public non-sealed interface GlobArrayField extends Field, Function<FieldValuesAccessor, Glob[]> {
    GlobType getTargetType();

    default Glob[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }

    default Stream<Glob> stream(FieldValuesAccessor fieldValuesAccessor) {
        return fieldValuesAccessor.stream(this);
    }
}
