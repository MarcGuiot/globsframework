package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.math.BigDecimal;

public interface GlobGetBigDecimalArrayAccessor extends GlobGetAccessor {
    BigDecimal[] get(Glob glob);
}
