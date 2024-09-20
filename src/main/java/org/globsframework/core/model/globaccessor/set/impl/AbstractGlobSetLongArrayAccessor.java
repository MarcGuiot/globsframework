package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetLongArrayAccessor;

abstract public class AbstractGlobSetLongArrayAccessor implements GlobSetLongArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((long[]) value));
    }

}
