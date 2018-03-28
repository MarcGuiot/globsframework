package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetBooleanAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, boolean value);

    void set(MutableGlob glob, Boolean value);

}
