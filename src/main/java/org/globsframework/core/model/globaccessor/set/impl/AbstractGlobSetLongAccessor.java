package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetLongAccessor;

abstract public class AbstractGlobSetLongAccessor implements GlobSetLongAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Long) value));
    }

    public void setNative(MutableGlob glob, long value) {
        set(glob, value);
    }
}
