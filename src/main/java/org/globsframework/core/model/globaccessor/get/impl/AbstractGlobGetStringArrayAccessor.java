package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetStringArrayAccessor;

abstract public class AbstractGlobGetStringArrayAccessor implements GlobGetStringArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
