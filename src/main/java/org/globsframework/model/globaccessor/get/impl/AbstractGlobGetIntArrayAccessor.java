package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetIntAccessor;
import org.globsframework.model.globaccessor.get.GlobGetIntArrayAccessor;

abstract public class AbstractGlobGetIntArrayAccessor implements GlobGetIntArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
