package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBooleanAccessor;
import org.globsframework.model.globaccessor.set.GlobSetLongAccessor;

abstract public class AbstractGlobSetBooleanAccessor implements GlobSetBooleanAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Boolean) value));
    }

    public void setNative(MutableGlob glob, boolean value) {
        set(glob, value);
    }
}
