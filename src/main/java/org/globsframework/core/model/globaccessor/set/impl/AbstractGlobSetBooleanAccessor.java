package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetBooleanAccessor;

abstract public class AbstractGlobSetBooleanAccessor implements GlobSetBooleanAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Boolean) value));
    }

    public void setNative(MutableGlob glob, boolean value) {
        set(glob, value);
    }
}
