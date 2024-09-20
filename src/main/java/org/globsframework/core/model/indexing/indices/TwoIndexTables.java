package org.globsframework.core.model.indexing.indices;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.indexing.IndexTables;
import org.globsframework.core.model.indexing.IndexedTable;

public final class TwoIndexTables implements IndexTables {
    private final IndexedTable firstIndexedTable;
    private final IndexedTable secondIndexedTable;

    public TwoIndexTables(IndexedTable firstIndexedTable, IndexedTable secondIndexedTable) {
        this.firstIndexedTable = firstIndexedTable;
        this.secondIndexedTable = secondIndexedTable;
    }

    public void remove(Field field, Object oldValue, Glob glob) {
        firstIndexedTable.remove(field, oldValue, glob);
        secondIndexedTable.remove(field, oldValue, glob);
    }

    public void add(Object newValue, Glob glob, Field field, Object oldValue) {
        firstIndexedTable.add(field, newValue, oldValue, glob);
        secondIndexedTable.add(field, newValue, oldValue, glob);
    }

    public void add(Glob glob) {
        firstIndexedTable.add(glob);
        secondIndexedTable.add(glob);
    }

    public void remove(Glob glob) {
        firstIndexedTable.remove(glob);
        secondIndexedTable.remove(glob);
    }

    public IndexTables add(IndexedTable indexedTable) {
        return new ManyIndexTables(firstIndexedTable, secondIndexedTable, indexedTable);
    }

    public void removeAll() {
        firstIndexedTable.removeAll();
        secondIndexedTable.removeAll();
    }
}
