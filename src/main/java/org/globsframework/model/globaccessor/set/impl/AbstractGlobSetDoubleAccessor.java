package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetDoubleAccessor;

abstract public class AbstractGlobSetDoubleAccessor implements GlobSetDoubleAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Double) value));
    }

    public void setNative(MutableGlob glob, double value) {
        set(glob, value);
    }
}
