package org.globsframework.metamodel.fields;

import org.globsframework.model.FieldValuesAccessor;

import java.math.BigDecimal;
import java.util.function.Function;

public non-sealed interface BigDecimalField extends Field, Function<FieldValuesAccessor, BigDecimal> {
    default BigDecimal apply(FieldValuesAccessor glob) {
        return glob.get(this);
    }
}
