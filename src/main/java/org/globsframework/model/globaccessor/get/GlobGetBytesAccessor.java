package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

public interface GlobGetBytesAccessor extends GlobGetAccessor {
    byte[] get(Glob glob);
}
