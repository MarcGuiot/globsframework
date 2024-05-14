package org.globsframework.model.indexing.indices;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.indexing.IndexTables;
import org.globsframework.model.indexing.IndexedTable;

public final class OneIndexTable implements IndexTables {
    private final IndexedTable indexedTable;

    public OneIndexTable(IndexedTable indexedTable) {
        this.indexedTable = indexedTable;
    }

    public void remove(Field field, Object oldValue, Glob glob) {
        indexedTable.remove(field, oldValue, glob);
    }

    public void add(Object newValue, Glob glob, Field field, Object oldValue) {
        indexedTable.add(field, newValue, oldValue, glob);
    }

    public void add(Glob glob) {
        indexedTable.add(glob);
    }

    public void remove(Glob glob) {
        indexedTable.remove(glob);
    }

    public IndexTables add(IndexedTable indexedTable) {
        return new TwoIndexTables(this.indexedTable, indexedTable);
    }

    public void removeAll() {
        indexedTable.removeAll();
    }
}
