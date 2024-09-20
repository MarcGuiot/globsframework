package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetDateTimeAccessor;

abstract public class AbstractGlobGetDateTimeAccessor implements GlobGetDateTimeAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
