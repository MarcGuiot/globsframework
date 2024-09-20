package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

import java.math.BigDecimal;

public interface GlobSetBigDecimalAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, BigDecimal value);
}
