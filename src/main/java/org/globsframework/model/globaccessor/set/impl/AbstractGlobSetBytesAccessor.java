package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetBytesAccessor;
import org.globsframework.model.globaccessor.set.GlobSetDateAccessor;

import java.time.LocalDate;

abstract public class AbstractGlobSetBytesAccessor implements GlobSetBytesAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((byte[]) value));
    }
}
