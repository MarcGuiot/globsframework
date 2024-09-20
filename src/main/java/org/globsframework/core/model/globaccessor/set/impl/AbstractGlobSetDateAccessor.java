package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetDateAccessor;

import java.time.LocalDate;

abstract public class AbstractGlobSetDateAccessor implements GlobSetDateAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((LocalDate) value));
    }

}
