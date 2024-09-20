package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetGlobArrayAccessor extends GlobGetAccessor {
    Glob[] get(Glob glob);
}
