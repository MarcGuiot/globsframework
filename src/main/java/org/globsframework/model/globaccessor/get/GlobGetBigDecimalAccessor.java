package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.math.BigDecimal;

public interface GlobGetBigDecimalAccessor extends GlobGetAccessor {
    BigDecimal get(Glob glob);
}
