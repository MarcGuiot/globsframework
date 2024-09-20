package org.globsframework.core.utils.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapOfMaps<KEY1, KEY2, VALUE> extends MapOfMaps<KEY1, KEY2, VALUE> {

    public ConcurrentMapOfMaps() {
    }

    public ConcurrentMapOfMaps(ConcurrentMapOfMaps<KEY1, KEY2, VALUE> maps) {
        super(maps);
    }

    Map<KEY1, Map<KEY2, VALUE>> createMap() {
        return new ConcurrentHashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap() {
        return new ConcurrentHashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap(Map<KEY2, VALUE> value) {
        return new ConcurrentHashMap<>(value);
    }

    public Object clone() {
        return new ConcurrentMapOfMaps<KEY1, KEY2, VALUE>(this);
    }

}
