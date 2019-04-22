package org.globsframework.utils.container;

import java.util.Iterator;
import java.util.Map;

class MapContainer<K extends Comparable, V> implements Container<K, V> {
    private Map<K, V> map;

    public MapContainer(Map<K, V> map) {
        this.map = map;
    }

    public V get(K key) {
        return map.get(key);
    }

    public Container<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Iterator<V> values() {
        return map.values().iterator();
    }

    public V remove(K key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public <E extends Functor<K, V>>  E apply(E functor) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            functor.apply(entry.getKey(), entry.getValue());
        }
        return functor;
    }

    public V first() {
        return map.values().iterator().next();
    }
}
