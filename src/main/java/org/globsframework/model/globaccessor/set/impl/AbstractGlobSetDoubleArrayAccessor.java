package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBooleanArrayAccessor;
import org.globsframework.model.globaccessor.set.GlobSetDoubleArrayAccessor;

abstract public class AbstractGlobSetDoubleArrayAccessor implements GlobSetDoubleArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((double[]) value));
    }

}
