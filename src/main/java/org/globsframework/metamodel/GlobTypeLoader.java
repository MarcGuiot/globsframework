package org.globsframework.metamodel;

import org.globsframework.metamodel.index.MultiFieldNotUniqueIndex;
import org.globsframework.metamodel.index.MultiFieldUniqueIndex;
import org.globsframework.metamodel.index.NotUniqueIndex;
import org.globsframework.metamodel.index.UniqueIndex;

public interface GlobTypeLoader {
    GlobTypeLoader load();

    GlobType getType();

    GlobTypeLoader defineUniqueIndex(UniqueIndex index, Field field);

    GlobTypeLoader defineNonUniqueIndex(NotUniqueIndex index, Field field);

    GlobTypeLoader defineMultiFieldUniqueIndex(MultiFieldUniqueIndex index, Field... fields);

    GlobTypeLoader defineMultiFieldNotUniqueIndex(MultiFieldNotUniqueIndex index, Field... fields);

    <T> GlobTypeLoader register(Class<T> klass, T t);
}
