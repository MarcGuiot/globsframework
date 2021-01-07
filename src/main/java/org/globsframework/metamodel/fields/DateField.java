package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.time.LocalDate;
import java.util.function.Function;

public interface DateField extends Field, Function<Glob, LocalDate> {
    default LocalDate apply(Glob glob) {
        return glob.get(this);
    }
}
