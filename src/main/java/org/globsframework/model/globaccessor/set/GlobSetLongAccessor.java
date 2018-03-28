package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetLongAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, long value);

    void set(MutableGlob glob, Long value);

}
