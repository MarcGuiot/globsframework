package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetBigDecimalAccessor;

import java.math.BigDecimal;

abstract public class AbstractGlobSetBigDecimalAccessor implements GlobSetBigDecimalAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((BigDecimal) value));
    }

}
