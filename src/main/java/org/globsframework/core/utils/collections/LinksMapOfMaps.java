package org.globsframework.core.utils.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinksMapOfMaps<KEY1, KEY2, VALUE> extends MapOfMaps<KEY1, KEY2, VALUE> {

    public LinksMapOfMaps() {
    }

    public LinksMapOfMaps(LinksMapOfMaps<KEY1, KEY2, VALUE> maps) {
        super(maps);
    }

    Map<KEY1, Map<KEY2, VALUE>> createMap() {
        return new LinkedHashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap() {
        return new LinkedHashMap<>();
    }

    Map<KEY2, VALUE> createInnerMap(Map<KEY2, VALUE> value) {
        return new LinkedHashMap<>(value);
    }

    public Object clone() {
        return new LinksMapOfMaps<KEY1, KEY2, VALUE>(this);
    }

}
