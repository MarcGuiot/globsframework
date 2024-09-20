package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

import java.time.LocalDate;

public interface GlobSetDateAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, LocalDate value);
}
