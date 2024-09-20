package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetBytesAccessor;

abstract public class AbstractGlobGetBytesAccessor implements GlobGetBytesAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
