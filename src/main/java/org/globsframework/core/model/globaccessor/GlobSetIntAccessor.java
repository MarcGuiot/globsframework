package org.globsframework.core.model.globaccessor;

import org.globsframework.core.model.Glob;

public interface GlobSetIntAccessor extends GlobSetAccessor {

    void set(Glob glob, int value);

    void set(Glob glob, Integer value);

}
