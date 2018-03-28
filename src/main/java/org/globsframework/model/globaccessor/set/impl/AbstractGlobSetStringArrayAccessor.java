package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetStringAccessor;
import org.globsframework.model.globaccessor.set.GlobSetStringArrayAccessor;

abstract public class AbstractGlobSetStringArrayAccessor implements GlobSetStringArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((String[]) value));
    }

}
