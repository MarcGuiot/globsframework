package org.globsframework.core.model.indexing.indices;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.indexing.IndexedTable;

import java.util.*;

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
        Set<Glob> newSet = index.computeIfAbsent(value, k -> new HashSet<Glob>());
        newSet.add(glob);
    }

    public List<Glob> findByIndex(Object value) {
        Set<Glob> globs = index.get(value);
        if (globs == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(globs);
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
