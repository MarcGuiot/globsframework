package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetStringArrayAccessor;

abstract public class AbstractGlobSetStringArrayAccessor implements GlobSetStringArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((String[]) value));
    }

}
