package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetBooleanArrayAccessor extends GlobGetAccessor {
    boolean[] get(Glob glob);
}
