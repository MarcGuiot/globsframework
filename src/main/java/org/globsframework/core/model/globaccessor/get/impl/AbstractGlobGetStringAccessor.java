package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetStringAccessor;

abstract public class AbstractGlobGetStringAccessor implements GlobGetStringAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
