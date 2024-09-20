package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetDoubleArrayAccessor;

abstract public class AbstractGlobSetDoubleArrayAccessor implements GlobSetDoubleArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((double[]) value));
    }

}
