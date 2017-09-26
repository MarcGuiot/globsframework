package org.globsframework.metamodel;

import org.globsframework.metamodel.index.MultiFieldNotUniqueIndex;
import org.globsframework.metamodel.index.MultiFieldUniqueIndex;
import org.globsframework.metamodel.index.NotUniqueIndex;
import org.globsframework.metamodel.index.UniqueIndex;

public interface GlobTypeLoader {
   GlobTypeLoader load();

   GlobType getType();

   void defineUniqueIndex(UniqueIndex index, Field field);

   void defineNonUniqueIndex(NotUniqueIndex index, Field field);

   void defineMultiFieldUniqueIndex(MultiFieldUniqueIndex index, Field... fields);

   void defineMultiFieldNotUniqueIndex(MultiFieldNotUniqueIndex index, Field... fields);

   <T> GlobTypeLoader register(Class<T> klass, T t);
}
