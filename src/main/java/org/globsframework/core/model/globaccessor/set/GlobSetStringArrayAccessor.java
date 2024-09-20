package org.globsframework.core.model.globaccessor.set;

import org.globsframework.core.model.MutableGlob;

public interface GlobSetStringArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, String[] value);

}
