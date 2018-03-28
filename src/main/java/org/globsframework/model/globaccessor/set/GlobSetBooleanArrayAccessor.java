package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetBooleanArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, boolean[] value);

}
