package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetIntAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, int value);

    void set(MutableGlob glob, Integer value);
}
