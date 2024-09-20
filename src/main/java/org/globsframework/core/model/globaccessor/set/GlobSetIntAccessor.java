package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetIntAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, int value);

    void set(MutableGlob glob, Integer value);
}
