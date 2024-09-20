package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetBigDecimalArrayAccessor;

import java.math.BigDecimal;

abstract public class AbstractGlobSetBigDecimalArrayAccessor implements GlobSetBigDecimalArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((BigDecimal[]) value));
    }

}
