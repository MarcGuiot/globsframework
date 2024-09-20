package org.globsframework.core.utils.container;

import org.globsframework.core.utils.Utils;

import java.util.*;

public class ArrayContainer<T extends Comparable, D> implements Container<T, D> {
    static int SIZE = 8;
    private Object elements[] = new Object[SIZE * 2];
    private int elementCount = 1;


    public ArrayContainer(T key, D value) {
        elements[0] = key;
        elements[1] = value;
    }

    public D get(T key) {
        int i1 = binarySearch(key);
        if (i1 < 0) {
            return null;
        }
        return (D) elements[(i1 << 1) + 1];
    }

    public Container<T, D> put(T key, D value) {
        int index = binarySearch(key);
        if (index >= 0 || elementCount < SIZE) {
            if (index < 0) {
                index = -index - 1;
                index <<= 1;
                for (int i = (elementCount << 1) - 1; i >= index; i--) {
                    elements[i + 2] = elements[i];
                }
                elementCount++;
            } else {
                index <<= 1;
            }
            elements[index] = key;
            elements[index + 1] = value;
            return this;
        } else {
            SortedMap<T, D> tmp = new TemporaryTreeSet<T, D>(this); //to use the fact that the array is already sorded. treemap only use entryset.iterator()
            TreeMap<T, D> map = new TreeMap<T, D>(tmp);
            map.put(key, value);
            return new MapContainer<T, D>(map);
        }
    }

    public boolean isEmpty() {
        return elementCount == 0;
    }

    public Iterator<D> values() {
        return new ArrayIterator<D>();
    }

    public D remove(T key) {
        int index = binarySearch(key);
        if (index < 0) {
            return null;
        }
        D oldValue = (D) elements[(index << 1) + 1];

        index <<= 1;
        for (; index < ((elementCount << 1) - 2); index++) {
            elements[index] = elements[index + 2];
        }
        elementCount--;
        return oldValue;
    }

    public int size() {
        return elementCount;
    }

    public <E extends Functor<T, D>> E apply(E functor) {
        for (int i = 0; i < elementCount; i++) {
            functor.apply((T) elements[(i << 1)], (D) elements[(i << 1) + 1]);
        }
        return functor;
    }

    public D first() {
        return (D) elements[1];
    }

    private class ArrayIterator<D> implements Iterator<D> {
        int pos = 0;

        public boolean hasNext() {
            return pos < elementCount;
        }

        public D next() {
            D d = (D) elements[(pos << 1) + 1];
            pos++;
            return d;
        }

        public void remove() {
            elementCount--;
            pos--;
            for (int i = pos << 1; i < elementCount << 1; i++) {
                elements[i] = elements[i + 2];
            }
        }
    }


    private int binarySearch(Object key) {
        int low = 0;
        int high = elementCount - 1;
        Object[] a = elements;

        if (key == null) {
            if (elementCount > 0) {
                return elements[0] == null ? 0 : -1;
            }
            return -1;
        }

        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable midVal = (Comparable) a[mid << 1];
            int cmp;
            if (midVal == null) {
                cmp = -1;
            } else {
                cmp = midVal.compareTo(key);
            }

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }


    static class TemporaryTreeSet<T extends Comparable, D> implements SortedMap<T, D> {
        private ArrayContainer<T, D> container;

        public TemporaryTreeSet(ArrayContainer<T, D> container) {
            this.container = container;
        }

        public Comparator<? super T> comparator() {
            return Utils.NullAwareComparator.INSTANCE;
        }

        public SortedMap<T, D> subMap(T fromKey, T toKey) {
            throw new RuntimeException("Not implemented");
        }

        public SortedMap<T, D> headMap(T toKey) {
            throw new RuntimeException("Not implemented");
        }

        public SortedMap<T, D> tailMap(T fromKey) {
            throw new RuntimeException("Not implemented");
        }

        public T firstKey() {
            throw new RuntimeException("Not implemented");
        }

        public T lastKey() {
            throw new RuntimeException("Not implemented");
        }

        public int size() {
            return container.size();
        }

        public boolean isEmpty() {
            throw new RuntimeException("Not implemented");
        }

        public boolean containsKey(Object key) {
            throw new RuntimeException("Not implemented");
        }

        public boolean containsValue(Object value) {
            throw new RuntimeException("Not implemented");
        }

        public D get(Object key) {
            return container.get((T) key);
        }

        public D put(T key, D value) {
            throw new RuntimeException("Not implemented");
        }

        public D remove(Object key) {
            throw new RuntimeException("Not implemented");
        }

        public void putAll(Map<? extends T, ? extends D> m) {
            throw new RuntimeException("Not implemented");
        }

        public void clear() {
            throw new RuntimeException("Not implemented");
        }

        public Set<T> keySet() {
            throw new RuntimeException("Not implemented");
        }

        public Collection<D> values() {
            throw new RuntimeException("Not implemented");
        }

        public Set<Entry<T, D>> entrySet() {
            return new Set<Entry<T, D>>() {
                public int size() {
                    throw new RuntimeException("Not implemented");
                }

                public boolean isEmpty() {
                    throw new RuntimeException("Not implemented");
                }

                public boolean contains(Object o) {
                    throw new RuntimeException("Not implemented");
                }

                public Iterator<Entry<T, D>> iterator() {
                    return new ContainerEntryIterator(container);
                }

                public Object[] toArray() {
                    throw new RuntimeException("Not implemented");
                }

                public <T> T[] toArray(T[] a) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean add(Entry<T, D> tdEntry) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean remove(Object o) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean containsAll(Collection<?> c) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean addAll(Collection<? extends Entry<T, D>> c) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean retainAll(Collection<?> c) {
                    throw new RuntimeException("Not implemented");
                }

                public boolean removeAll(Collection<?> c) {
                    throw new RuntimeException("Not implemented");
                }

                public void clear() {
                    throw new RuntimeException("Not implemented");
                }
            };
        }

        private static class ContainerEntryIterator<T extends Comparable, D> implements Iterator<Entry<T, D>>, Entry<T, D> {
            private final ArrayContainer<T, D> container;
            private int pos;

            public ContainerEntryIterator(ArrayContainer<T, D> container) {
                this.container = container;
                this.pos = -1;
            }

            public boolean hasNext() {
                return pos + 1 < container.elementCount;
            }

            public Entry<T, D> next() {
                pos++;
                return this;
            }

            public void remove() {
                throw new RuntimeException("Not implemented");
            }

            public T getKey() {
                return (T) container.elements[(pos << 1)];
            }

            public D getValue() {
                return (D) container.elements[(pos << 1) + 1];
            }

            public D setValue(D value) {
                throw new RuntimeException("Not implemented");
            }
        }
    }
}
