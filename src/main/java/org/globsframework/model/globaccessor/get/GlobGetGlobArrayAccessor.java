package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

public interface GlobGetGlobArrayAccessor extends GlobGetAccessor {
    Glob[] get(Glob glob);
}
