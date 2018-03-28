package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.time.LocalDate;

public interface GlobGetDateAccessor extends GlobGetAccessor {
    LocalDate get(Glob glob);
}
