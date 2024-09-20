package org.globsframework.core.model.globaccessor;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetDoubleAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, double value);

    void set(MutableGlob glob, Double value);

}
