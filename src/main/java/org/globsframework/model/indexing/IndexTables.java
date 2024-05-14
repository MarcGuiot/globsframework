package org.globsframework.model.indexing;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.model.Glob;

public interface IndexTables {
    void add(Object newValue, Glob glob, Field field, Object oldValue);

    void add(Glob glob);

    IndexTables add(IndexedTable indexedTable);

    void remove(Glob glob);

    void remove(Field field, Object oldValue, Glob glob);

    void removeAll();
}
