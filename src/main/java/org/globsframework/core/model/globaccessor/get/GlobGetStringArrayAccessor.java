package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetStringArrayAccessor extends GlobGetAccessor {
    String[] get(Glob glob);
}
