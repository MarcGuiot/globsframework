package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetDoubleAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, double value);

    void set(MutableGlob glob, Double value);

}
