package org.globsframework.core.model.globaccessor.set.impl;

import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.set.GlobSetDateTimeAccessor;

import java.time.ZonedDateTime;

abstract public class AbstractGlobSetDateTimeAccessor implements GlobSetDateTimeAccessor {
    public void setValue(MutableGlob glob, Object value) {
        set(glob, ((ZonedDateTime) value));
    }

}
