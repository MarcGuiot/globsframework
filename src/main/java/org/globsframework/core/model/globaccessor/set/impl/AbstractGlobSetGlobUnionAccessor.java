package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetGlobAccessor;

abstract public class AbstractGlobSetGlobUnionAccessor implements GlobSetGlobAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Glob) value));
    }

}
