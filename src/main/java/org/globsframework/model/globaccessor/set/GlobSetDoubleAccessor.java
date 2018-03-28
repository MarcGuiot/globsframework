package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetDoubleAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, double value);

    void set(MutableGlob glob, Double value);

}
