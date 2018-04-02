package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetDateAccessor;
import org.globsframework.model.globaccessor.get.GlobGetGlobAccessor;

abstract public class AbstractGlobGetGlobAccessor implements GlobGetGlobAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
