package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetLongArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, long[] value);

}
