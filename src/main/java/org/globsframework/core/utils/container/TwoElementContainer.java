package org.globsframework.core.utils.container;

import org.globsframework.core.utils.Utils;

import java.util.Iterator;

class TwoElementContainer<T extends Comparable, D> implements Container<T, D> {
    private T key1;
    private D value1;
    private T key2;
    private D value2;

    public TwoElementContainer(OneElementContainer<T, D> elementContainer, T key, D value) {
        key1 = elementContainer.getKey();
        value1 = elementContainer.getValue();
        this.key2 = key;
        this.value2 = value;
        swapIfNeed();
    }

    public D get(T key) {
        return unset() ? null : Utils.compare(this.key1, key) == 0 ? this.value1 : Utils.compare(this.key2, key) == 0 ? this.value2 : null;
    }

    private boolean unset() {
        return value1 == null && key1 == null && value2 == null && key2 == null;
    }

    public Container<T, D> put(T key, D value) {
        if (key1 == null && value1 == null || Utils.compare(key1, key) == 0) {
            key1 = key;
            value1 = value;
            return this;
        } else if (key2 == null && (value2 == null) || Utils.compare(key2, key) == 0) {
            this.key2 = key;
            this.value2 = value;
            swapIfNeed();
            return this;
        } else {
            ArrayContainer<T, D> container = new ArrayContainer<T, D>(this.key1, this.value1);
            container.put(key2, value2);
            container.put(key, value);
            return container;
        }
    }

    private void swapIfNeed() {
        if (key1 == null) {
            return;
        }
        if (key2 == null) {
            key2 = key1;
            key1 = null;
            D v = value2;
            value2 = value1;
            value1 = v;
            return;
        }
        if (key1.compareTo(key2) > 0) {
            T k = key2;
            key2 = key1;
            key1 = k;
            D v = value2;
            value2 = value1;
            value1 = v;
        }
    }

    public boolean isEmpty() {
        return unset();
    }

    public Iterator<D> values() {
        if (isEmpty()) {
            return EmptyContainer.ITERATOR;
        }
        if (key1 == null && value1 == null) {
            return new SecondIterator();
        }
        if (key2 == null && value2 == null) {
            return new FirstIterator();
        }
        return new TwoStepIterator();
    }

    public D remove(T key) {
        if (this.key1 == key || (this.key1 != null && this.key1.compareTo(key) == 0)) {
            D oldValue = value1;
            value1 = value2;
            this.key1 = key2;
            key2 = null;
            value2 = null;
            return oldValue;
        }
        if (this.key2 == key || (this.key2 != null && this.key2.compareTo(key) == 0)) {
            D oldValue = value2;
            value2 = null;
            this.key2 = null;
            return oldValue;
        }
        return null;
    }

    public int size() {
        return unset() ? 0 : (isValue1Set() && isValue2Set()) ? 2 : 1;
    }

    private boolean isValue1Set() {
        return key1 != null || value1 != null;
    }

    private boolean isValue2Set() {
        return key2 != null || value2 != null;
    }

    public <E extends Functor<T, D>> E apply(E functor) {
        if (isValue1Set()) {
            functor.apply(key1, value1);
        }
        if (isValue2Set()) {
            functor.apply(key2, value2);
        }
        return functor;
    }

    public D first() {
        return value1;
    }

    private class TwoStepIterator implements Iterator<D> {
        private int count = 0;

        public boolean hasNext() {
            if (count == 2) {
                return false;
            }
            return true;
        }

        public D next() {
            count++;
            if (count == 1) {
                return value1;
            }
            if (count == 2) {
                return value2;
            }
            throw new RuntimeException("out of bound");
        }

        public void remove() {
            if (count == 1) {
                key1 = null;
                value1 = null;
            } else if (count == 2) {
                key2 = null;
                value2 = null;
            }
        }
    }

    private class FirstIterator implements Iterator<D> {
        boolean hasNext = true;

        public boolean hasNext() {
            return hasNext;
        }

        public D next() {
            hasNext = false;
            return value1;
        }

        public void remove() {
            key1 = null;
            value1 = null;
        }
    }

    private class SecondIterator implements Iterator<D> {
        boolean hasNext = true;

        public boolean hasNext() {
            return hasNext;
        }

        public D next() {
            hasNext = false;
            return value2;
        }

        public void remove() {
            key2 = null;
            value2 = null;
        }
    }
}
