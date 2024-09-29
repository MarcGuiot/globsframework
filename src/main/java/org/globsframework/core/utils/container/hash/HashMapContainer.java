package org.globsframework.core.utils.container.hash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapContainer<T, D> implements HashContainer<T, D> {
    HashMap<T, D> elements;

    public HashMapContainer(int size) {
        elements = new HashMap<>((int) (size / 0.75), 0.75f);
    }

    public HashMapContainer(T key, D value) {
        elements = new HashMap<>((int) (8 / 0.75), 0.75f);
        elements.put(key, value);
    }

    public D get(T key) {
        return elements.get(key);
    }

    public HashContainer<T, D> put(T key, D value) {
        elements.put(key, value);
        return this;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public Iterator<D> values() {
        return elements.values().iterator();
    }

    public TwoElementIterator<T, D> entryIterator() {
        return new TwoElementIterator<T, D>() {
            Iterator<Map.Entry<T, D>> iterator = elements.entrySet().iterator();
            Map.Entry<T, D> entry = null;

            public boolean next() {
                boolean b = iterator.hasNext();
                if (b) {
                    entry = iterator.next();
                }
                return b;
            }

            public T getKey() {
                return entry.getKey();
            }

            public D getValue() {
                return entry.getValue();
            }
        };
    }

    public D remove(T key) {
        return elements.remove(key);
    }

    public int size() {
        return elements.size();
    }

    public <E extends Functor<T, D>> E forEach(E functor) {
        for (Map.Entry<T, D> tdEntry : elements.entrySet()) {
            functor.apply(tdEntry.getKey(), tdEntry.getValue());
        }
        return functor;
    }

    public boolean containsKey(T key) {
        return elements.containsKey(key);
    }

    public <E extends FunctorAndRemove<T, D>> E applyAndRemoveIfTrue(E functor) {
        for (Iterator<Map.Entry<T, D>> iterator = elements.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<T, D> tdEntry = iterator.next();
            if (functor.apply(tdEntry.getKey(), tdEntry.getValue())) {
                iterator.remove();
            }
        }
        return functor;
    }

}
