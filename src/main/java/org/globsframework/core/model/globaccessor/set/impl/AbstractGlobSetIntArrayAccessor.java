package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetIntArrayAccessor;

abstract public class AbstractGlobSetIntArrayAccessor implements GlobSetIntArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((int[]) value));
    }

}
