package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

public interface GlobGetStringArrayAccessor extends GlobGetAccessor {
    String[] get(Glob glob);
}
