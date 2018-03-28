package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.time.LocalDate;

public interface GlobGetBlobAccessor extends GlobGetAccessor {
    byte[] get(Glob glob);
}
