package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetIntAccessor;

abstract public class AbstractGlobGetIntAccessor implements GlobGetIntAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

    public int get(Glob glob, int defaultValueIfNull) {
        Integer value = get(glob);
        return value == null ? defaultValueIfNull : value;
    }

    public int getNative(Glob glob) {
        return get(glob, 0);
    }

}
