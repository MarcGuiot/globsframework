package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValuesAccessor;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface GlobField extends Field, Function<FieldValuesAccessor, Glob> {
    GlobType getTargetType();

    default Glob apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
