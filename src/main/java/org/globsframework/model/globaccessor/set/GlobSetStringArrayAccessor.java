package org.globsframework.model.globaccessor.set;

import org.globsframework.model.MutableGlob;

public interface GlobSetStringArrayAccessor extends GlobSetAccessor {

    void set(MutableGlob glob, String[] value);

}
