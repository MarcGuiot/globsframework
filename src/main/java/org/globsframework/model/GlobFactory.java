package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.globaccessor.GlobSetAccessor;
import org.globsframework.model.globaccessor.GlobSetDoubleAccessor;
import org.globsframework.model.globaccessor.GlobSetIntAccessor;

public interface GlobFactory {

   MutableGlob create();

//   GlobSetAccessor get(Field field);
//
//   GlobSetIntAccessor get(IntegerField field);
//
//   GlobSetDoubleAccessor get(DoubleField field);
//
//   GlobSetIntAccessor adapt(Field field); //=> convert type int, long, double, boolean, String to int

}
