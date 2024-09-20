package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

import java.time.ZonedDateTime;

public interface GlobSetDateTimeAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, ZonedDateTime value);
}
