package org.globsframework.model.indexing.indices;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.indexing.IndexTables;
import org.globsframework.model.indexing.IndexedTable;

public final class ManyIndexTables implements IndexTables {
    private final IndexedTable indexedTables[];

    public ManyIndexTables(IndexedTable firstIndexedTable, IndexedTable secondIndexedTable, IndexedTable indexedTable) {
        indexedTables = new IndexedTable[3];
        indexedTables[0] = firstIndexedTable;
        indexedTables[1] = secondIndexedTable;
        indexedTables[2] = indexedTable;
    }

    private ManyIndexTables(ManyIndexTables tables, IndexedTable indexedTable) {
        indexedTables = new IndexedTable[tables.indexedTables.length + 1];
        int i;
        for (i = 0; i < tables.indexedTables.length; i++) {
            indexedTables[i] = tables.indexedTables[i];
        }
        indexedTables[i] = indexedTable;
    }

    public void remove(Field field, Object oldValue, Glob glob) {
        for (IndexedTable indexedTable : indexedTables) {
            indexedTable.remove(field, oldValue, glob);
        }
    }

    public void add(Object newValue, Glob glob, Field field, Object oldValue) {
        for (IndexedTable indexedTable : indexedTables) {
            indexedTable.add(field, newValue, oldValue, glob);
        }
    }

    public void add(Glob glob) {
        for (IndexedTable indexedTable : indexedTables) {
            indexedTable.add(glob);
        }
    }

    public void remove(Glob glob) {
        for (IndexedTable indexedTable : indexedTables) {
            indexedTable.remove(glob);
        }
    }

    public IndexTables add(IndexedTable indexedTable) {
        return new ManyIndexTables(this, indexedTable);
    }

    public void removeAll() {
        for (IndexedTable indexedTable : indexedTables) {
            indexedTable.removeAll();
        }
    }
}
