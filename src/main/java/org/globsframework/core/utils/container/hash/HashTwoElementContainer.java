package org.globsframework.core.utils.container.hash;

import org.globsframework.core.utils.Utils;

import java.util.Collections;
import java.util.Iterator;

public class HashTwoElementContainer<T, D> implements HashContainer<T, D> {
    private T key1;
    private D value1;
    private T key2;
    private D value2;

    public HashTwoElementContainer() {
        key1 = (T) HashOneElementContainer.NULL;
        key2 = (T) HashOneElementContainer.NULL;
    }

    public HashTwoElementContainer(HashOneElementContainer<T, D> elementContainer, T key, D value) {
        key1 = elementContainer.getKey();
        value1 = elementContainer.getValue();
        this.key2 = key;
        this.value2 = value;
    }

    public D get(T key) {
        return Utils.equalWithHash(key, this.key1) ? value1 : Utils.equalWithHash(key, this.key2) ? value2 : null;
    }

    public HashContainer<T, D> put(T key, D value) {
        if (key1 == HashOneElementContainer.NULL || Utils.equalWithHash(key, this.key1)) {
            key1 = key;
            value1 = value;
            return this;
        } else if (key2 == HashOneElementContainer.NULL || Utils.equalWithHash(key, this.key2)) {
            this.key2 = key;
            this.value2 = value;
            return this;
        } else {
            return new Hash4ElementContainer<T, D>(this, key, value);
        }
    }

    public boolean isEmpty() {
        return key1 == HashOneElementContainer.NULL && key2 == HashOneElementContainer.NULL;
    }

    public Iterator<D> values() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        }
        if (key1 == HashOneElementContainer.NULL) {
            return new SecondIterator();
        }
        if (key2 == HashOneElementContainer.NULL) {
            return new FirstIterator();
        }
        return new TwoStepIterator();
    }

    public TwoElementIterator<T, D> entryIterator() {
        return new TwoElementIterator<T, D>() {
            int i = 0;
            T key;
            D value;

            public boolean next() {
                switch (i) {
                    case 0:
                        i++;
                        if (key1 != HashOneElementContainer.NULL) {
                            key = key1;
                            value = value1;
                            return true;
                        }
                    case 1:
                        i++;
                        if (key2 != HashOneElementContainer.NULL) {
                            key = key2;
                            value = value2;
                            return true;
                        }
                }
                return false;
            }

            public T getKey() {
                return key;
            }

            public D getValue() {
                return value;
            }
        };
    }

    public D remove(T key) {
        if (Utils.equalWithHash(key, this.key1)) {
            D oldValue = value1;
            key1 = (T) HashOneElementContainer.NULL;
            value1 = null;
            return oldValue;
        }
        if (Utils.equalWithHash(key, this.key2)) {
            D oldValue = value2;
            key2 = (T) HashOneElementContainer.NULL;
            value2 = null;
            return oldValue;
        }
        return null;
    }

    public int size() {
        int count = 0;
        if (this.key1 != HashOneElementContainer.NULL) {
            count++;
        }
        if (this.key2 != HashOneElementContainer.NULL) {
            count++;
        }
        return count;
    }

    private boolean isValue1Set() {
        return key1 != HashOneElementContainer.NULL;
    }

    private boolean isValue2Set() {
        return key2 != HashOneElementContainer.NULL;
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

    public <E extends FunctorAndRemove<T, D>> E applyAndRemoveIfTrue(E functor) {
        if (isValue1Set()) {
            if (functor.apply(key1, value1)) {
                key1 = (T) HashOneElementContainer.NULL;
            }
        }
        if (isValue2Set()) {
            if (functor.apply(key2, value2)) {
                key2 = (T) HashOneElementContainer.NULL;
            }
        }
        return functor;
    }

    public T getKey1() {
        return key1;
    }

    public T getKey2() {
        return key2;
    }

    public D getValue1() {
        return value1;
    }

    public D getValue2() {
        return value2;
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
                key1 = (T) HashOneElementContainer.NULL;
                value1 = null;
            } else if (count == 2) {
                key2 = (T) HashOneElementContainer.NULL;
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
            key1 = (T) HashOneElementContainer.NULL;
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
            key2 = (T) HashOneElementContainer.NULL;
            value2 = null;
        }
    }
}
