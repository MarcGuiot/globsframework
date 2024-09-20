package org.globsframework.core.utils.collections;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentUniqueMultiMap<K, V> {
    private final Map<K, Set<V>> values = new ConcurrentHashMap<>();

    public void put(K k, V v) {
        values.compute(k, (k1, vs) -> {
            if (vs == null) {
                vs = new HashSet<>();
            }
            vs.add(v);
            return vs;
        });
    }

    public Set<V> get(K k) {
        return values.get(k);
    }

    public void remove(K k) {
        values.remove(k);
    }

    public <T extends BiFunction<K, Set<V>, Set<V>>>
    T compute(K k, T kSetSetBiFunction) {
        values.compute(k, kSetSetBiFunction);
        return kSetSetBiFunction;
    }

    public void removeValue(V v) {
        values.entrySet().removeIf(kListEntry -> {
            kListEntry.getValue().remove(v);
            return kListEntry.getValue().isEmpty();
        });
    }

    public void remove(K k, V v) {
        values.computeIfPresent(k, (k1, vs) -> {
            vs.remove(v);
            if (vs.isEmpty()) {
                return null;
            }
            return vs;
        });
    }
}
