package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.util.function.Function;
import java.util.stream.Stream;

public non-sealed interface GlobArrayField extends Field, Function<FieldValuesAccessor, Glob[]> {
    GlobType getTargetType();

    default Glob[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }

    default Stream<Glob> stream(FieldValuesAccessor fieldValuesAccessor){
        return fieldValuesAccessor.stream(this);
    }
}
