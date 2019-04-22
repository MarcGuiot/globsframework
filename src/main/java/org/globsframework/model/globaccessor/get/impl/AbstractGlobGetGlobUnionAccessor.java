package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetGlobAccessor;

abstract public class AbstractGlobGetGlobUnionAccessor implements GlobGetGlobAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
