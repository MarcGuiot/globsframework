package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetDateAccessor;

abstract public class AbstractGlobGetDateAccessor implements GlobGetDateAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
