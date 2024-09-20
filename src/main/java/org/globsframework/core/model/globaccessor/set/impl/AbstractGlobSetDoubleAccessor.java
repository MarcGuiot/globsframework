package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetDoubleAccessor;

abstract public class AbstractGlobSetDoubleAccessor implements GlobSetDoubleAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Double) value));
    }

    public void setNative(MutableGlob glob, double value) {
        set(glob, value);
    }
}
