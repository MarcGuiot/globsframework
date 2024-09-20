package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetDoubleArrayAccessor;

abstract public class AbstractGlobGetDoubleArrayAccessor implements GlobGetDoubleArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
