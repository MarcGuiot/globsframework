package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.time.LocalDate;

public interface GlobGetGlobAccessor extends GlobGetAccessor {
    Glob get(Glob glob);
}
