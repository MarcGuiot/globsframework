package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetGlobAccessor;
import org.globsframework.model.globaccessor.get.GlobGetGlobArrayAccessor;

abstract public class AbstractGlobGetGlobArrayAccessor implements GlobGetGlobArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
