package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetBooleanArrayAccessor;

abstract public class AbstractGlobSetBooleanArrayAccessor implements GlobSetBooleanArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((boolean[]) value));
    }

}
