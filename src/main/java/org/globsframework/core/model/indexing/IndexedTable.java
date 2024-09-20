package org.globsframework.core.model.indexing;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;

import java.util.List;

public interface IndexedTable {
    void add(Glob glob);

    void add(Field field, Object newValue, Object oldValue, Glob glob);

    List<Glob> findByIndex(Object value);

    boolean remove(Field field, Object value, Glob glob);

    boolean remove(Glob glob);

    void removeAll();
}
