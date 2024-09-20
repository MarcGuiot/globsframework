package org.globsframework.core.directory;

import org.globsframework.core.utils.exceptions.ItemAlreadyExists;

public interface Directory {

    <T> T find(Class<T> serviceClass);

    <T> T get(Class<T> serviceClass);

    boolean contains(Class serviceClass);

    <T, D extends T> void add(Class<T> serviceClass, D service) throws ItemAlreadyExists;

    void add(Object service) throws ItemAlreadyExists;

    <T, D extends T> void addFactory(Class<T> serviceClass, Factory<D> factory) throws ItemAlreadyExists;

    void registerCleaner(Cleanable cleanable);

    void clean();

    interface Factory<D> {
        D create();
    }
}
