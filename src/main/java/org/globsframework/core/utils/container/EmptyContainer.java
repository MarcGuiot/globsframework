package org.globsframework.core.utils.container;

import java.util.Iterator;

class EmptyContainer<T extends Comparable, D> implements Container<T, D> {
    static final EmptyIterator ITERATOR = new EmptyIterator();

    public D get(T key) {
        return null;
    }

    public Container<T, D> put(T key, D value) {
        return new OneElementContainer<T, D>(key, value);
    }

    public boolean isEmpty() {
        return true;
    }

    public Iterator<D> values() {
        return ITERATOR;
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

    public D first() {
        return null;
    }

    private static class EmptyIterator implements Iterator {
        public boolean hasNext() {
            return false;
        }

        public Object next() {
            return null;
        }

        public void remove() {
        }
    }
}
