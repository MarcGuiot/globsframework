package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetBooleanArrayAccessor;

abstract public class AbstractGlobGetBooleanArrayAccessor implements GlobGetBooleanArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
