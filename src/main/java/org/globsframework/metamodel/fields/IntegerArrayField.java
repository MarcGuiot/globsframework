package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.function.Function;

public interface IntegerArrayField extends Field, Function<Glob, int[]> {
    default int[] apply(Glob glob){
        return glob.get(this);
    }
}
