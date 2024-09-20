package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetStringAccessor;

abstract public class AbstractGlobSetStringAccessor implements GlobSetStringAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((String) value));
    }

}
