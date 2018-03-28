package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBooleanArrayAccessor;

abstract public class AbstractGlobSetBooleanArrayAccessor implements GlobSetBooleanArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((boolean[]) value));
    }

}
