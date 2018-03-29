package org.globsframework.model.globaccessor;

import org.globsframework.model.MutableGlob;

public interface GlobSetDoubleAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, double value);

    void set(MutableGlob glob, Double value);

}
