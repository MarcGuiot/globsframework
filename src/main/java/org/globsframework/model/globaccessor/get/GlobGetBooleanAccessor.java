package org.globsframework.model.globaccessor.get;

import org.globsframework.model.Glob;

public interface GlobGetBooleanAccessor extends GlobGetAccessor {
    boolean get(Glob glob, boolean defaultValueIfNull); // return defaultValueIfNull only if

    boolean getNative(Glob glob); // return the field default value if one is available or 0

    Boolean get(Glob glob);
}
