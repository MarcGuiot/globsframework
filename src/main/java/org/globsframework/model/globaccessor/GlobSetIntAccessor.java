package org.globsframework.model.globaccessor;

import org.globsframework.model.Glob;

public interface GlobSetIntAccessor extends GlobSetAccessor {

   void set(Glob glob, int value);

   void set(Glob glob, Integer value);

}
