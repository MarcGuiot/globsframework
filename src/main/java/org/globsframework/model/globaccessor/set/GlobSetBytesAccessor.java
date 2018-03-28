package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetBytesAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, byte[] value);

}
