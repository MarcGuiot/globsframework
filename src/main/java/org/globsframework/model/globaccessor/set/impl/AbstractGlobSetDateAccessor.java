package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetDateAccessor;
import org.globsframework.model.globaccessor.set.GlobSetStringAccessor;

import java.time.LocalDate;

abstract public class AbstractGlobSetDateAccessor implements GlobSetDateAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((LocalDate) value));
    }

}
