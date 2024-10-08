package org.globsframework.core.model.globaccessor.get;

import org.globsframework.core.model.Glob;

public interface GlobGetDoubleAccessor extends GlobGetAccessor {
    double get(Glob glob, double defaultValueIfNull); // return defaultValueIfNull only if

    double getNative(Glob glob); // return the field default value if one is available or 0

    Double get(Glob glob);
}
