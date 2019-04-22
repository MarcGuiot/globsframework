package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetGlobArrayAccessor;

abstract public class AbstractGlobSetGlobUnionArrayAccessor implements GlobSetGlobArrayAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Glob[]) value));
    }

}
