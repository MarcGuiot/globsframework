package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.math.BigDecimal;
import java.util.function.Function;

public interface BigDecimalArrayField extends Field, Function<Glob, BigDecimal[]> {
    default BigDecimal[] apply(Glob glob) {
        return glob.get(this);
    }
}
