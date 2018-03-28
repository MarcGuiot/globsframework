package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

public interface GlobGetLongArrayAccessor extends GlobGetAccessor {
    long[] get(Glob glob);
}
