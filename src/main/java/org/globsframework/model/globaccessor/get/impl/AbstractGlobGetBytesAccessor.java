package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetBytesAccessor;

abstract public class AbstractGlobGetBytesAccessor implements GlobGetBytesAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
