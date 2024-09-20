package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetLongArrayAccessor;

abstract public class AbstractGlobGetLongArrayAccessor implements GlobGetLongArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
