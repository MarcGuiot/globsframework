package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetIntArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, int[] value);

}
