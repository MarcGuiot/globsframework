package org.globsframework.utils.collections;

import org.globsframework.utils.Strings;

import java.util.*;

public class MultiSetMap<K, V> {
    private Map<K, Set<V>> map = new HashMap<>();

    public void put(K key, V value) {
        Set<V> values = getOrCreateList(key);
        values.add(value);
    }

    public void putAll(K key, Collection<V> values) {
        Set<V> list = getOrCreateList(key);
        list.addAll(values);
    }

    private Set<V> getOrCreateList(K key) {
        Set<V> values = map.get(key);
        if (values == null) {
            values = createNewList();
            map.put(key, values);
        }
        return values;
    }

    protected Set<V> createNewList() {
        Set<V> values;
        values = new HashSet<>();
        return values;
    }

    public boolean isEmpty() {
        for (Set<V> set : map.values()) {
            if (!set.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        int result = 0;
        for (Set<V> set : map.values()) {
            result += set.size();
        }
        return result;
    }

    public Set<V> get(K key) {
        Set<V> values = map.get(key);
        if (values == null) {
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableSet(values);
    }

    public Set<Map.Entry<K, Set<V>>> entries() {
        return map.entrySet();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<K, Set<V>> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());
            builder.append(Strings.LINE_SEPARATOR);
        }
        return builder.toString();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public Set<V> remove(K k) {
        return map.remove(k);
    }

    public boolean removeValue(V value) {
        boolean removed = false;
        for (Set<V> set : map.values()) {
            removed |= set.remove(value);
        }
        return removed;
    }

    public boolean removeValue(K key, V value) {
        Set<V> set = map.get(key);
        if (set != null) {
            boolean find = set.remove(value);
            if (set.isEmpty()) {
                map.remove(key);
            }
            return find;
        }
        return false;
    }

    public void putUnique(K key, V value) {
        Set<V> set = map.get(key);
        if (set != null) {
            if (!set.contains(value)) {
                set.add(value);
            }
        }
        else {
            Set<V> values = createNewList();
            map.put(key, values);
            values.add(value);
        }
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(K key) {
        Set<V> result = map.get(key);
        return !(result == null || result.isEmpty());
    }

    public MultiSetMap<K, V> duplicate() {
        MultiSetMap<K, V> result = new MultiSetMap<K, V>();
        for (Map.Entry<K, Set<V>> entry : map.entrySet()) {
            final K key = entry.getKey();
            final Set<V> set = createNewList();
            set.addAll(entry.getValue());
            result.map.put(key, set);
        }
        return result;
    }
}
