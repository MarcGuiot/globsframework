package org.globsframework.utils.container.intkey;

public interface IntKeyContainer<T> {

    T get(int key);

    IntKeyContainer<T> put(int key, T elem);

    IntKeyContainer<T> remove(int key);

    IntKeyContainer<T> clear();

    void apply(Functor<T> functor);

    interface Functor<T> {
        void apply(int key, T value);
    }

}
