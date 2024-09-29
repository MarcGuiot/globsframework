package org.globsframework.core.utils.container.specific;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.Utils;
import org.globsframework.core.utils.container.hash.HashContainer;

import java.util.Collections;
import java.util.Iterator;

public class HashTwoGlobKeyContainer implements HashContainer<Key, Glob> {
    private Glob value1;
    private Glob value2;

    public HashTwoGlobKeyContainer() {
        value1 = null;
        value2 = null;
    }

    public HashTwoGlobKeyContainer(HashOneGlobKeyContainer elementContainer, Glob value) {
        value1 = elementContainer.getValue();
        this.value2 = value;
    }

    public Glob get(Key key) {
        return (value1 != null && Utils.equalWithHash(key, this.value1.getKey())) ? value1 :
                value2 != null && Utils.equalWithHash(key, this.value2.getKey()) ? value2 : null;
    }

    public HashContainer<Key, Glob> put(Key key, Glob value) {
        HashOneGlobKeyContainer.checkEqual(key, value);
        if (value1 == null || Utils.equalWithHash(key, this.value1.getKey())) {
            value1 = value;
            return this;
        } else if (value2 == null || Utils.equalWithHash(key, this.value2.getKey())) {
            this.value2 = value;
            return this;
        } else {
            return new Hash4GlobKeyContainer(this, value);
        }
    }

    public boolean isEmpty() {
        return value1 == null && value2 == null;
    }

    public Iterator<Glob> values() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        }
        if (value1 == null) {
            return new SecondIterator();
        }
        if (value2 == null) {
            return new FirstIterator();
        }
        return new TwoStepIterator();
    }

    public TwoElementIterator<Key, Glob> entryIterator() {
        return new TwoElementIterator<Key, Glob>() {
            int i = 0;
            Glob value;

            public boolean next() {
                switch (i) {
                    case 0:
                        i++;
                        if (value1 != null) {
                            value = value1;
                            return true;
                        }
                    case 1:
                        i++;
                        if (value2 != null) {
                            value = value2;
                            return true;
                        }
                }
                return false;
            }

            public Key getKey() {
                return value.getKey();
            }

            public Glob getValue() {
                return value;
            }
        };
    }

    public Glob remove(Key key) {
        if (value1 != null && Utils.equalWithHash(key, this.value1.getKey())) {
            Glob oldValue = value1;
            value1 = null;
            return oldValue;
        }
        if (value2 != null && Utils.equalWithHash(key, this.value2.getKey())) {
            Glob oldValue = value2;
            value2 = null;
            return oldValue;
        }
        return null;
    }

    public int size() {
        int count = 0;
        if (this.value1 != null) {
            count++;
        }
        if (this.value2 != null) {
            count++;
        }
        return count;
    }

    private boolean isValue1Set() {
        return value1 != null;
    }

    private boolean isValue2Set() {
        return value2 != null;
    }

    public <E extends Functor<Key, Glob>> E forEach(E functor) {
        if (isValue1Set()) {
            functor.apply(value1.getKey(), value1);
        }
        if (isValue2Set()) {
            functor.apply(value2.getKey(), value2);
        }
        return functor;
    }

    public boolean containsKey(Key key) {
        return value1 != null && Utils.equalWithHash(value1.getKey(), key)
                || value2 != null && Utils.equalWithHash(value2.getKey(),key);
    }

    public <E extends FunctorAndRemove<Key, Glob>> E applyAndRemoveIfTrue(E functor) {
        if (isValue1Set()) {
            if (functor.apply(value1.getKey(), value1)) {
                value1 = null;
            }
        }
        if (isValue2Set()) {
            if (functor.apply(value2.getKey(), value2)) {
                value2 = null;
            }
        }
        return functor;

    }

    public Key getKey1() {
        return value1.getKey();
    }

    public Key getKey2() {
        return value2.getKey();
    }

    public Glob getValue1() {
        return value1;
    }

    public Glob getValue2() {
        return value2;
    }

    private class TwoStepIterator implements Iterator<Glob> {
        private int count = 0;

        public boolean hasNext() {
            if (count == 2) {
                return false;
            }
            return true;
        }

        public Glob next() {
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
                value1 = null;
            } else if (count == 2) {
                value2 = null;
            }
        }
    }

    private class FirstIterator implements Iterator<Glob> {
        boolean hasNext = true;

        public boolean hasNext() {
            return hasNext;
        }

        public Glob next() {
            hasNext = false;
            return value1;
        }

        public void remove() {
            value1 = null;
        }
    }

    private class SecondIterator implements Iterator<Glob> {
        boolean hasNext = true;

        public boolean hasNext() {
            return hasNext;
        }

        public Glob next() {
            hasNext = false;
            return value2;
        }

        public void remove() {
            value2 = null;
        }
    }
}
