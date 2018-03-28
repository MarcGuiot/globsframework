package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetIntArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, int[] value);

}
