package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

import java.time.LocalDate;

public interface GlobSetDateAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, LocalDate value);
}
