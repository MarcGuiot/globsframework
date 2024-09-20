package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetGlobAccessor;

abstract public class AbstractGlobGetGlobAccessor implements GlobGetGlobAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
