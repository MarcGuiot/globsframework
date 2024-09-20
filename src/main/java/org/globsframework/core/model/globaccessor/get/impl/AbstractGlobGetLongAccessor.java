package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetLongAccessor;

abstract public class AbstractGlobGetLongAccessor implements GlobGetLongAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

    public long get(Glob glob, long defaultValueIfNull) {
        Long value = get(glob);
        if (value == null) {
            return defaultValueIfNull;
        } else {
            return value;
        }
    }

    public long getNative(Glob glob) {
        return get(glob, 0l);
    }
}
