package org.globsframework.model.indexing.indices;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.indexing.IndexedTable;
import org.globsframework.model.utils.EmptyGlobList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultNotUniqueIndex implements IndexedTable {
    private Field field;
    private Map<Object, Set<Glob>> index = new HashMap<Object, Set<Glob>>();

    public DefaultNotUniqueIndex(Field field) {
        this.field = field;
    }

    public boolean remove(Field field, Object value, Glob glob) {
        Set<Glob> keyGlobMap = index.get(value);
        return keyGlobMap != null && keyGlobMap.remove(glob);
    }

    public void add(Field field, Object newValue, Object oldValue, Glob glob) {
        final Set<Glob> oldSet = index.get(oldValue);
        if (oldSet != null) {
            oldSet.remove(glob);
        }
        Set<Glob> newSet = index.get(newValue);
        if (newSet == null) {
            newSet = new HashSet<Glob>();
            index.put(newValue, newSet);
        }
        newSet.add(glob);
    }

    public void add(Glob glob) {
        Object value = glob.getValue(field);
        Set<Glob> newSet = index.get(value);
        if (newSet == null) {
            newSet = new HashSet<Glob>();
            index.put(value, newSet);
        }
        newSet.add(glob);
    }

    public GlobList findByIndex(Object value) {
        Set<Glob> globs = index.get(value);
        if (globs == null) {
            return new EmptyGlobList();
        }
        return new GlobList(globs);
    }

    public boolean remove(Glob glob) {
        Object value = glob.getValue(field);
        Set<Glob> globs = index.get(value);
        boolean removed = false;
        if (globs != null) {
            removed = globs.remove(glob);
            if (globs.isEmpty()) {
                index.remove(value);
            }
        }
        return removed;
    }

    public void removeAll() {
        index.clear();
    }
}
