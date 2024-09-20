package org.globsframework.core.utils.container.hash;

import java.util.Collections;
import java.util.Iterator;

class HashEmptyContainer<T, D> implements HashContainer<T, D> {

    public D get(T key) {
        return null;
    }

    public HashContainer<T, D> put(T key, D value) {
        return new HashOneElementContainer<T, D>(key, value);
    }

    public boolean isEmpty() {
        return true;
    }

    public Iterator<D> values() {
        return Collections.emptyIterator();
    }

    public TwoElementIterator<T, D> entryIterator() {
        return EmptyTwoElementIterator.INSTANCE;
    }

    public D remove(T value) {
        return null;
    }

    public int size() {
        return 0;
    }

    public <E extends Functor<T, D>> E apply(E functor) {
        return functor;
    }

    public <E extends FunctorAndRemove<T, D>> E applyAndRemoveIfTrue(E functor) {
        return functor;
    }

    public long computeSize() {
        return 0;
    }

    private static class EmptyTwoElementIterator<T, D> implements TwoElementIterator<T, D> {
        static EmptyTwoElementIterator INSTANCE = new EmptyTwoElementIterator<>();

        public boolean next() {
            return false;
        }

        public T getKey() {
            return null;
        }

        public D getValue() {
            return null;
        }
    }
}
