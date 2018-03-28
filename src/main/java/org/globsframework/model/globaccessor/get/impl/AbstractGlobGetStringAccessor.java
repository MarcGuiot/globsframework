package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetStringAccessor;

abstract public class AbstractGlobGetStringAccessor implements GlobGetStringAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
