package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetBooleanArrayAccessor;

abstract public class AbstractGlobGetBooleanArrayAccessor implements GlobGetBooleanArrayAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

}
