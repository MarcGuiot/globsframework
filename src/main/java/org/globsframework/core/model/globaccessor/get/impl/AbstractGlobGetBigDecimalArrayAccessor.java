package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetBigDecimalArrayAccessor;

abstract public class AbstractGlobGetBigDecimalArrayAccessor implements GlobGetBigDecimalArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
