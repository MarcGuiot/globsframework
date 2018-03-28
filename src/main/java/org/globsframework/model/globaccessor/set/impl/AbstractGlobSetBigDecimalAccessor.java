package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBigDecimalAccessor;
import org.globsframework.model.globaccessor.set.GlobSetBigDecimalArrayAccessor;

import java.math.BigDecimal;

abstract public class AbstractGlobSetBigDecimalAccessor implements GlobSetBigDecimalAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((BigDecimal) value));
    }

}
