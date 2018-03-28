package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetDateAccessor;
import org.globsframework.model.globaccessor.get.GlobGetDateTimeAccessor;

abstract public class AbstractGlobGetDateTimeAccessor implements GlobGetDateTimeAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
