package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetLongArrayAccessor;
import org.globsframework.model.globaccessor.set.GlobSetStringArrayAccessor;

abstract public class AbstractGlobSetLongArrayAccessor implements GlobSetLongArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((long[]) value));
    }

}
