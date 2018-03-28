package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetLongArrayAccessor;

abstract public class AbstractGlobGetLongArrayAccessor implements GlobGetLongArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
