package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetBytesAccessor;

abstract public class AbstractGlobSetBytesAccessor implements GlobSetBytesAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((byte[]) value));
    }
}
