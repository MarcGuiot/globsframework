package org.globsframework.core.utils.container;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashGlobContainer implements GlobContainer {
    Map<Key, Glob> map;

    public HashGlobContainer(GlobContainer globContainer) {
        map = new HashMap<>(globContainer.size());
        globContainer.apply(new Functor() {
            public void apply(Glob d) {
                map.put(d.getKey(), d);
            }
        });
    }

    public Glob get(Key key) {
        return map.get(key);
    }

    public GlobContainer put(Key key, Glob value) {
        map.put(key, value);
        return this;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Iterator<Glob> values() {
        return map.values().iterator();
    }

    public Glob remove(Key value) {
        return map.remove(value);
    }

    public int size() {
        return map.size();
    }

    public <E extends Functor> E apply(E functor) {
        for (Glob glob : map.values()) {
            functor.apply(glob);
        }
        return functor;
    }
}
