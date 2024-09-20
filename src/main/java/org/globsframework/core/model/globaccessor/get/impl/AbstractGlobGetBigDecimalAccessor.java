package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetBigDecimalAccessor;

abstract public class AbstractGlobGetBigDecimalAccessor implements GlobGetBigDecimalAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
