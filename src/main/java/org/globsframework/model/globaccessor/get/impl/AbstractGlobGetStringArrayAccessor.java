package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetStringArrayAccessor;

abstract public class AbstractGlobGetStringArrayAccessor implements GlobGetStringArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
