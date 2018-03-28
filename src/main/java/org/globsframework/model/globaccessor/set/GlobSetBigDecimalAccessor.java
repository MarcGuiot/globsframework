package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

import java.math.BigDecimal;

public interface GlobSetBigDecimalAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, BigDecimal value);
}
