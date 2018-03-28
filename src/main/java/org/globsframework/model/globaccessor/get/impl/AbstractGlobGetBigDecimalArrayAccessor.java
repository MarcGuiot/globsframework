package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetBigDecimalArrayAccessor;
import org.globsframework.model.globaccessor.get.GlobGetStringAccessor;

abstract public class AbstractGlobGetBigDecimalArrayAccessor implements GlobGetBigDecimalArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
