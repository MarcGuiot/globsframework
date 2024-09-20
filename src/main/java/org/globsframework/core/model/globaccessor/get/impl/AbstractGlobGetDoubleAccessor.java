package org.globsframework.core.model.globaccessor.get.impl;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.globaccessor.get.GlobGetDoubleAccessor;

abstract public class AbstractGlobGetDoubleAccessor implements GlobGetDoubleAccessor {

    public Object getValue(Glob glob) {
        return get(glob);
    }

    public double get(Glob glob, double defaultValueIfNull) {
        Double value = get(glob);
        return value == null ? defaultValueIfNull : value;
    }

    public double getNative(Glob glob) {
        return get(glob, 0.);
    }

}
