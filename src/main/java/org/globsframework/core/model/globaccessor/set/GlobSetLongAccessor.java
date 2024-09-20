package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetLongAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, long value);

    void set(MutableGlob glob, Long value);

}
