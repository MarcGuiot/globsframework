package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetBooleanAccessor;

abstract public class AbstractGlobGetBooleanAccessor implements GlobGetBooleanAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

    public boolean get(Glob glob, boolean defaultValueIfNull) {
        Boolean value = get(glob);
        return value == null ? defaultValueIfNull : value;
    }

    public boolean getNative(Glob glob) {
        return get(glob, Boolean.FALSE);
    }

}
