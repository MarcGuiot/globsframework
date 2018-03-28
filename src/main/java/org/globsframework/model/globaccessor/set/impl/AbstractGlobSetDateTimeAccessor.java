package org.globsframework.model.globaccessor.set.impl;

import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.set.GlobSetDateTimeAccessor;
import org.globsframework.model.globaccessor.set.GlobSetStringAccessor;

import java.time.ZonedDateTime;

abstract public class AbstractGlobSetDateTimeAccessor implements GlobSetDateTimeAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((ZonedDateTime) value));
    }

}
