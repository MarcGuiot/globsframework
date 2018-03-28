package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetDoubleArrayAccessor;

abstract public class AbstractGlobGetDoubleArrayAccessor implements GlobGetDoubleArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
