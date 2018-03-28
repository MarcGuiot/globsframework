package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetIntAccessor;

abstract public class AbstractGlobSetIntAccessor implements GlobSetIntAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Integer) value));
    }

    public void setNative(MutableGlob glob, int value) {
        set(glob, value);
    }
}
