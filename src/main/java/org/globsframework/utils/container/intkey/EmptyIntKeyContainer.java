package org.globsframework.utils.container.intkey;

public class EmptyIntKeyContainer<T> implements IntKeyContainer<T> {

    public static EmptyIntKeyContainer INSTANCE = new EmptyIntKeyContainer();

    public T get(int key) {
        return null;
    }

    public IntKeyContainer<T> put(int key, T elem) {
        return new OneOptimizedIntKeyContainer<>(key, elem, this);
    }

    public IntKeyContainer<T> remove(int key) {
        return this;
    }

    public IntKeyContainer<T> clear() {
        return this;
    }

    public void apply(Functor<T> functor) {
    }
}
