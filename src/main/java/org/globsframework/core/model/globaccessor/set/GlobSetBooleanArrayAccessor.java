package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetBooleanArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, boolean[] value);

}
