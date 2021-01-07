package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.time.ZonedDateTime;
import java.util.function.Function;

public interface DateTimeField extends Field, Function<Glob, ZonedDateTime> {
    default ZonedDateTime apply(Glob glob) {
        return glob.get(this);
    }
}
