package org.globsframework.core.utils.container.hash;

import org.globsframework.core.utils.Utils;

import java.util.Collections;
import java.util.Iterator;

public class HashOneElementContainer<T, D> implements HashContainer<T, D> {
    public static Object NULL = new Object();
    private T key;
    private D value;

    public HashOneElementContainer() {
        key = (T) NULL;
        value = null;
    }

    public HashOneElementContainer(T key, D value) {
        this.key = key;
        this.value = value;
    }

    public D get(T key) {
        return Utils.equal(key, this.key) ? value : null;
    }

    public HashContainer<T, D> put(T key, D value) {
        if (this.key == HashOneElementContainer.NULL || Utils.equal(key, this.key)) {
            this.key = key;
            this.value = value;
            return this;
        } else {
            return new HashTwoElementContainer<T, D>(this, key, value);
        }
    }

    public boolean isEmpty() {
        return NULL == key;
    }

    public Iterator<D> values() {
        if (key == NULL) {
            return Collections.emptyIterator();
        }
        return new OneStepIterator();
    }

    public HashContainer<T, D> duplicate() {
        return new HashOneElementContainer<>(this.key, this.value);
    }

    public TwoElementIterator<T, D> entryIterator() {

        return new TwoElementIterator<T, D>() {
            boolean isNext = key != NULL;

            public boolean next() {
                boolean isNext = this.isNext;
                this.isNext = false;
                return isNext;
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
        if (Utils.equalWithHash(key, this.key)) {
            D oldValue = value;
            value = null;
            this.key = (T) HashOneElementContainer.NULL;
            return oldValue;
        }
        return null;
    }

    public int size() {
        return key == NULL ? 0 : 1;
    }

    public <E extends Functor<T, D>> E forEach(E functor) {
        if (key != NULL) {
            functor.apply(key, value);
        }
        return functor;
    }

    public boolean containsKey(T key) {
        return Utils.equalWithHash(key, this.key);
    }

    public <E extends FunctorAndRemove<T, D>> E applyAndRemoveIfTrue(E functor) {
        if (key != NULL) {
            if (functor.apply(key, value)) {
                key = (T) NULL;
            }
        }
        return functor;
    }

    public T getKey() {
        return key;
    }

    public D getValue() {
        return value;
    }

    private class OneStepIterator implements Iterator<D> {
        boolean hasNext = true;

        public boolean hasNext() {
            if (hasNext) {
                return true;
            }
            return false;
        }

        public D next() {
            D value1 = value;
            hasNext = false;
            return value1;
        }

        public void remove() {
            value = null;
            key = (T) HashOneElementContainer.NULL;
        }
    }
}
