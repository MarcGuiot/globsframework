package org.globsframework.core.utils.collections;

import java.util.*;

public class MapOfMaps<KEY1, KEY2, VALUE> {
    private Map<KEY1, Map<KEY2, VALUE>> maps = createMap();

    Map<KEY1, Map<KEY2, VALUE>> createMap() {
        return new HashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap() {
        return new HashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap(Map<KEY2, VALUE> value) {
        return new HashMap<>(value);
    }

    public MapOfMaps() {
    }

    public MapOfMaps(MapOfMaps<KEY1, KEY2, VALUE> maps) {
        for (Map.Entry<KEY1, Map<KEY2, VALUE>> mapEntry : maps.maps.entrySet()) {
            this.maps.put(mapEntry.getKey(), createInnerMap(mapEntry.getValue()));
        }
    }

    public VALUE put(KEY1 key1, KEY2 key2, VALUE value) {
        Map<KEY2, VALUE> map = maps.computeIfAbsent(key1, (KEY1 key11) -> createInnerMap());
        return map.put(key2, value);
    }

    public VALUE get(KEY1 key1, KEY2 key2) {
        Map<KEY2, VALUE> map = maps.get(key1);
        if (map == null) {
            return null;
        }
        return map.get(key2);
    }

    public Map<KEY2, VALUE> get(KEY1 key1) {
        return maps.getOrDefault(key1, Collections.emptyMap());
    }

    public Map<KEY2, VALUE> getModifiable(KEY1 key1) {
        return maps.computeIfAbsent(key1, (KEY1 key11) -> createInnerMap());
    }

    public Iterator<VALUE> iterator() {
        return new MapOfMapIterator();
    }

    public Set<Map.Entry<KEY1, Map<KEY2, VALUE>>> entry() {
        return maps.entrySet();
    }

    public VALUE remove(KEY1 key1, KEY2 key2) {
        Map<KEY2, VALUE> map = maps.get(key1);
        if (map != null) {
            VALUE value = map.remove(key2);
            if (map.isEmpty()) {
                maps.remove(key1);
            }
            return value;
        }
        return null;
    }

    public Collection<VALUE> values(KEY1 key1) {
        Map<KEY2, VALUE> map = maps.get(key1);
        if (map != null) {
            return map.values();
        }
        return Collections.EMPTY_LIST;
    }

    public Collection<VALUE> values() {
        List<VALUE> result = new ArrayList<VALUE>();
        for (Map map : maps.values()) {
            result.addAll(map.values());
        }
        return result;
    }

    public MapOfMaps<KEY1, KEY2, VALUE> merge(MapOfMaps<KEY1, KEY2, VALUE> maps) {
        Set<Map.Entry<KEY1, Map<KEY2, VALUE>>> entries = maps.maps.entrySet();
        for (Map.Entry<KEY1, Map<KEY2, VALUE>> entry : entries) {
            this.maps.compute(entry.getKey(), (key1, key2VALUEMap) -> {
                if (key2VALUEMap == null) {
                    return entry.getValue();
                } else {
                    key2VALUEMap.putAll(entry.getValue());
                    return key2VALUEMap;
                }
            });
        }
        return this;
    }

    public int size() {
        int count = 0;
        for (Map<KEY2, VALUE> key2VALUEMap : maps.values()) {
            count += key2VALUEMap.size();
        }
        return count;
    }

    public Set<KEY1> keys() {
        return maps.keySet();
    }

    public boolean contains(KEY1 key1) {
        return maps.containsKey(key1);
    }

    public boolean containsKey(KEY1 key1, KEY2 key2) {
        return maps.containsKey(key1) && maps.get(key1).containsKey(key2);
    }

    public void removeAll(KEY1 key1) {
        maps.remove(key1);
    }

    public boolean isEmpty() {
        if (maps.isEmpty()) {
            return true;
        }
        for (Map<KEY2, VALUE> map : maps.values()) {
            if (!map.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    public Object clone() {
        return new MapOfMaps<>(this);
    }

    public void putAll(KEY1 key, Map<KEY2, VALUE> values) {
        maps.merge(key, values, (oldValues, newValues) -> {
            oldValues.putAll(newValues);
            return oldValues;
        });
    }

    private class MapOfMapIterator implements Iterator<VALUE> {
        Iterator<Map<KEY2, VALUE>> iterator1;
        Iterator<VALUE> iterator2;
        VALUE value;

        public MapOfMapIterator() {
            iterator1 = maps.values().iterator();
        }

        public boolean hasNext() {
            if (iterator2 != null && iterator2.hasNext()) {
                return true;
            }
            while (iterator1.hasNext() && (iterator2 == null || !iterator2.hasNext())) {
                iterator2 = iterator1.next().values().iterator();
            }
            return iterator2 != null && iterator2.hasNext();
        }

        public VALUE next() {
            return iterator2.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

