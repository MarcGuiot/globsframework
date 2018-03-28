package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBigDecimalArrayAccessor;
import org.globsframework.model.globaccessor.set.GlobSetLongArrayAccessor;

import java.math.BigDecimal;

abstract public class AbstractGlobSetBigDecimalArrayAccessor implements GlobSetBigDecimalArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((BigDecimal[]) value));
    }

}
