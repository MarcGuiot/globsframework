package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetLongArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, long[] value);

}
