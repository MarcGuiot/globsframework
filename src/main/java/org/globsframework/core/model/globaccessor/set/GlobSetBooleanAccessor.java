package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetBooleanAccessor extends GlobSetAccessor {

    void setNative(MutableGlob glob, boolean value);

    void set(MutableGlob glob, Boolean value);

}
