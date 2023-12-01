package org.globsframework.utils.container;

import java.util.Iterator;

public interface Container<T extends Comparable, D> {
    Container EMPTY_INSTANCE = new EmptyContainer<>();

    static <T extends Comparable<?>, D> Container<T, D> empty(){
        return EMPTY_INSTANCE;
    }

    D get(T key);

    Container<T, D> put(T key, D value);

    boolean isEmpty();

    Iterator<D> values();

    D remove(T value);

    int size();

    <E extends Functor<T, D>> E apply(E functor);

    D first();

    interface Functor<T extends Comparable, D> {
        void apply(T key, D d);
    }
}
