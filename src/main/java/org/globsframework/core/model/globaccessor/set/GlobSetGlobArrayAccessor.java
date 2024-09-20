package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.MutableGlob;

public interface GlobSetGlobArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, Glob[] values);
}
