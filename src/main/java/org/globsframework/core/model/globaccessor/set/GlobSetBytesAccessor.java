package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetBytesAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, byte[] value);

}
