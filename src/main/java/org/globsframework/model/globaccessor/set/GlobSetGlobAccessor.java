package org.globsframework.model.globaccessor.set;

import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;

import java.time.LocalDate;

public interface GlobSetGlobAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, Glob value);
}
