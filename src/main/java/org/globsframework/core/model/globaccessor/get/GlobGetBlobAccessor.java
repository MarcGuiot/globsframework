package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetBlobAccessor extends GlobGetAccessor {
    byte[] get(Glob glob);
}
