package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface BlobField extends Field, Function<Glob, byte[]> {
    default byte[] apply(Glob glob) {
        return glob.get(this);
    }
}
