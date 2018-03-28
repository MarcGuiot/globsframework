package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetIntArrayAccessor;
import org.globsframework.model.globaccessor.set.GlobSetLongArrayAccessor;

abstract public class AbstractGlobSetIntArrayAccessor implements GlobSetIntArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((int[]) value));
    }

}
