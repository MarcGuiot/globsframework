package org.globsframework.core.utils.container.specific;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.Utils;
import org.globsframework.core.utils.container.hash.HashContainer;

import java.util.Collections;
import java.util.Iterator;

public class Hash4GlobKeyContainer implements HashContainer<Key, Glob> {
    private Glob value1;
    private Glob value2;
    private Glob value3;
    private Glob value4;

    public Hash4GlobKeyContainer() {
    }

    public Hash4GlobKeyContainer(HashTwoGlobKeyContainer elementContainer, Glob value) {
        value1 = elementContainer.getValue1();
        value2 = elementContainer.getValue2();
        this.value3 = value;
    }

    public Glob get(Key key) {
        return (value1 != null && Utils.equalWithHash(key, this.value1.getKey())) ? value1 :
                (value2 != null && Utils.equalWithHash(key, this.value2.getKey())) ? value2 :
                        (value3 != null && Utils.equalWithHash(key, this.value3.getKey())) ? value3 :
                                (value4 != null && Utils.equalWithHash(key, this.value4.getKey())) ? value4 : null;
    }

    public HashContainer<Key, Glob> put(Key key, Glob value) {
        HashOneGlobKeyContainer.checkEqual(key, value);
        if (value1 == null || Utils.equalWithHash(key, this.value1.getKey())) {
            value1 = value;
            return this;
        } else if (value2 == null || Utils.equalWithHash(key, this.value2.getKey())) {
            this.value2 = value;
            return this;
        } else if (value3 == null || Utils.equalWithHash(key, this.value3.getKey())) {
            value3 = value;
            return this;
        } else if (value4 == null || Utils.equalWithHash(key, this.value4.getKey())) {
            this.value4 = value;
            return this;
        } else {
            HashMapGlobKeyContainer container = new HashMapGlobKeyContainer(this);
            container.put(key, value);
            return container;
        }
    }

    public boolean isEmpty() {
        return value1 == null && value2 == null && value3 == null && value4 == null;
    }

    public Iterator<Glob> values() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        }
        return new Iterator<Glob>() {
            int i = 0;
            Glob value;

            public boolean hasNext() {
                switch (i) {
                    case 0:
                        i = 0;
                        if (value1 != null) {
                            value = value1;
                            break;
                        }
                    case 1:
                        i = 1;
                        if (value2 != null) {
                            value = value2;
                            break;
                        }
                    case 2:
                        i = 2;
                        if (value3 != null) {
                            value = value3;
                            break;
                        }
                    case 3:
                        i = 3;
                        if (value4 != null) {
                            value = value4;
                            break;
                        }
                    default:
                        i = 5;
                        break;

                }
                return i < 4;
            }

            public Glob next() {
                i++;
                return value;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
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
                    case 2:
                        i++;
                        if (value3 != null) {
                            value = value3;
                            return true;
                        }
                    case 3:
                        i++;
                        if (value4 != null) {
                            value = value4;
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
        if (value3 != null && Utils.equalWithHash(key, this.value3.getKey())) {
            Glob oldValue = value3;
            value3 = null;
            return oldValue;
        }
        if (value4 != null && Utils.equalWithHash(key, this.value4.getKey())) {
            Glob oldValue = value4;
            value4 = null;
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
        if (this.value3 != null) {
            count++;
        }
        if (this.value4 != null) {
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

    private boolean isValue3Set() {
        return value3 != null;
    }

    private boolean isValue4Set() {
        return value4 != null;
    }

    public <E extends Functor<Key, Glob>> E apply(E functor) {
        if (isValue1Set()) {
            functor.apply(value1.getKey(), value1);
        }
        if (isValue2Set()) {
            functor.apply(value2.getKey(), value2);
        }
        if (isValue3Set()) {
            functor.apply(value3.getKey(), value3);
        }
        if (isValue4Set()) {
            functor.apply(value4.getKey(), value4);
        }
        return functor;
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
        if (isValue3Set()) {
            if (functor.apply(value3.getKey(), value3)) {
                value3 = null;
            }
        }
        if (isValue4Set()) {
            if (functor.apply(value4.getKey(), value4)) {
                value4 = null;
            }
        }
        return functor;
    }
}
