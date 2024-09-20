package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetIntArrayAccessor;

abstract public class AbstractGlobGetIntArrayAccessor implements GlobGetIntArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
