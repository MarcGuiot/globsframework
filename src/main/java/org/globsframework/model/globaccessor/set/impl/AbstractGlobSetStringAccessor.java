package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetStringAccessor;

abstract public class AbstractGlobSetStringAccessor implements GlobSetStringAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((String) value));
    }

}
