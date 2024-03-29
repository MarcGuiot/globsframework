package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValuesAccessor;

import java.math.BigDecimal;
import java.util.function.Function;

public interface BigDecimalArrayField extends Field, Function<FieldValuesAccessor, BigDecimal[]> {
    default BigDecimal[] apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
