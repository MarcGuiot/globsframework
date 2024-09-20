package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetGlobArrayAccessor;

abstract public class AbstractGlobSetGlobArrayAccessor implements GlobSetGlobArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Glob[]) value));
    }

}
