package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetGlobArrayAccessor;

abstract public class AbstractGlobGetGlobArrayAccessor implements GlobGetGlobArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
