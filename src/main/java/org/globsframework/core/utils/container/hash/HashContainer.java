package org.globsframework.core.utils.container.hash;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface HashContainer<T, D> {
    HashContainer EMPTY_INSTANCE = new HashEmptyContainer<>();

    static <T, D> HashContainer<T, D> empty() {
        return EMPTY_INSTANCE;
    }

    D get(T key);

    HashContainer<T, D> put(T key, D value);

    boolean isEmpty();

    Iterator<D> values();

    TwoElementIterator<T, D> entryIterator();

    D remove(T value);

    int size();

    <E extends Functor<T, D>> E forEach(E functor);

    boolean containsKey(T key);

    default Stream<D> stream(){
        return StreamSupport.stream(Spliterators.spliterator(values(), size(), Spliterator.NONNULL), false);
    }

    interface Functor<T, D> {
        void apply(T key, D d);
    }

    <E extends FunctorAndRemove<T, D>> E applyAndRemoveIfTrue(E functor);

    interface FunctorAndRemove<T, D> {
        boolean apply(T key, D d);
    }

    class Helper {
        public static <T, D> HashContainer<T, D> allocate(int size) {
            switch (size) {
                case 0:
                    return EMPTY_INSTANCE;
                case 1:
                    return new HashOneElementContainer<T, D>();
                case 2:
                    return new HashTwoElementContainer<T, D>();
                case 4:
                    return new Hash4ElementContainer<T, D>();
                default:
                    return new HashMapContainer<T, D>(size);
            }
        }
    }

    interface TwoElementIterator<T, D> {

        boolean next();

        T getKey();

        D getValue();

    }
}
