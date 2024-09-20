package org.globsframework.core.utils.container;

import org.globsframework.core.utils.Utils;

import java.util.Iterator;

class OneElementContainer<T extends Comparable, D> implements Container<T, D> {
    private T key;
    private D value;

    public OneElementContainer(T key, D value) {
        this.key = key;
        this.value = value;
    }

    public D get(T key) {
        return unset() ? null : Utils.compare(this.key, key) == 0 ? value : null;
    }

    boolean unset() {
        return value == null && key == null;
    }

    public Container<T, D> put(T key, D value) {
        if (unset() || Utils.compare(this.key, key) == 0) {
            this.key = key;
            this.value = value;
            return this;
        } else {
            return new TwoElementContainer<T, D>(this, key, value);
        }
    }

    public boolean isEmpty() {
        return unset();
    }

    public Iterator<D> values() {
        if (unset()) {
            return EmptyContainer.ITERATOR;
        }
        return new OneStepIterator();
    }

    public D remove(T key) {
        if (!unset() && (Utils.compare(this.key, key) == 0)) {
            D oldValue = value;
            value = null;
            this.key = null;
            return oldValue;
        }
        return null;
    }

    public int size() {
        return unset() ? 0 : 1;
    }

    public <E extends Functor<T, D>> E apply(E functor) {
        if (!unset()) {
            functor.apply(key, value);
        }
        return functor;
    }

    public D first() {
        return value;
    }

    public T getKey() {
        return key;
    }

    public D getValue() {
        return value;
    }

    private class OneStepIterator implements Iterator<D> {
        boolean nextCall = false;

        public boolean hasNext() {
            if (nextCall) {
                return false;
            }
            nextCall = true;
            return true;
        }

        public D next() {
            return value;
        }

        public void remove() {
            value = null;
            key = null;
        }
    }
}
