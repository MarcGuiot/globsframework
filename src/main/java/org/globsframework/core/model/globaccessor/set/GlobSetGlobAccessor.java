package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.MutableGlob;

public interface GlobSetGlobAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, Glob value);
}
