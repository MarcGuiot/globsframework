package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface GlobGetDateTimeAccessor extends GlobGetAccessor {
    ZonedDateTime get(Glob glob);
}
