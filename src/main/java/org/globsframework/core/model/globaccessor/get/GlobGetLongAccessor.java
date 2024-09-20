package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetLongAccessor extends GlobGetAccessor {
    long get(Glob glob, long defaultValueIfNull); // return defaultValueIfNull only if

    long getNative(Glob glob); // return the field default value if one is available or 0

    Long get(Glob glob);
}
