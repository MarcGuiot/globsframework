package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

import java.time.ZonedDateTime;

public interface GlobSetDateTimeAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, ZonedDateTime value);
}
