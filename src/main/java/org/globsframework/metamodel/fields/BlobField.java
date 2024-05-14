package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.util.function.Function;

public non-sealed interface BlobField extends Field, Function<FieldValuesAccessor, byte[]> {
    default byte[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
