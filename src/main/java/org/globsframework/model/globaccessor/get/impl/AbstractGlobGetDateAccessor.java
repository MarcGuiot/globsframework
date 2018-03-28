package org.globsframework.model.globaccessor.get.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.globaccessor.get.GlobGetDateAccessor;

abstract public class AbstractGlobGetDateAccessor implements GlobGetDateAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }
}
