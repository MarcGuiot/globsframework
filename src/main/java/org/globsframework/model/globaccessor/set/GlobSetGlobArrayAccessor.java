package org.globsframework.model.globaccessor.set;

import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;

import java.time.LocalDate;

public interface GlobSetGlobArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, Glob[] values);
}
