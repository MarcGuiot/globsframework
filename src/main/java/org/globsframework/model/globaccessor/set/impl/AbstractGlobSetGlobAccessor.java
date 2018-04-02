package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetDateAccessor;
import org.globsframework.model.globaccessor.set.GlobSetGlobAccessor;

import java.time.LocalDate;

abstract public class AbstractGlobSetGlobAccessor implements GlobSetGlobAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((Glob) value));
    }

}
