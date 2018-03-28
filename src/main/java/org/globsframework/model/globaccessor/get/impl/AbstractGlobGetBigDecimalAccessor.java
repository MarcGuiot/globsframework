package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetBigDecimalAccessor;
import org.globsframework.model.globaccessor.get.GlobGetStringAccessor;

abstract public class AbstractGlobGetBigDecimalAccessor implements GlobGetBigDecimalAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
