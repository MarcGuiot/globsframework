package org.globsframework.core.utils.container.specific;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.container.hash.HashContainer;

import java.util.Collections;
import java.util.Iterator;

public class HashEmptyGlobContainer implements HashContainer<Key, Glob> {
    static final boolean fallback = Boolean.getBoolean("org.globsframework.globs.utils.container.specific.fallback");

    static private HashEmptyGlobContainer INSTANCE = new HashEmptyGlobContainer();

    public static class Helper {
        public static HashContainer<Key, Glob> allocate(int size) {
            if (fallback) {
                return HashContainer.Helper.allocate(size);
            }
            switch (size) {
                case 0:
                    return INSTANCE;
                case 1:
                    return new HashOneGlobKeyContainer();
                case 2:
                    return new HashTwoGlobKeyContainer();
                case 4:
                    return new Hash4GlobKeyContainer();
                default:
                    return new HashMapGlobKeyContainer(size);
            }
        }
    }

    public Glob get(Key key) {
        return null;
    }

    public HashContainer<Key, Glob> put(Key key, Glob value) {
        return new HashOneGlobKeyContainer(key, value);
    }

    public boolean isEmpty() {
        return true;
    }

    public Iterator<Glob> values() {
        return Collections.emptyIterator();
    }

    public TwoElementIterator<Key, Glob> entryIterator() {
        return EmptyTwoElementIterator.INSTANCE;
    }

    public Glob remove(Key value) {
        return null;
    }

    public int size() {
        return 0;
    }

    public <E extends Functor<Key, Glob>> E apply(E functor) {
        return functor;
    }

    public <E extends FunctorAndRemove<Key, Glob>> E applyAndRemoveIfTrue(E functor) {
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
