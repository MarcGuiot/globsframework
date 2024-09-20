package org.globsframework.core.utils.container.intkey;

public class OneOptimizedIntKeyContainer<T> implements IntKeyContainer<T> {

    private final int key;
    private T value;
    private IntKeyContainer<T> next;

    public OneOptimizedIntKeyContainer(int key, T value) {
        this.key = key;
        this.value = value;
        this.next = new EmptyIntKeyContainer<>();
    }

    public OneOptimizedIntKeyContainer(int key, T value, IntKeyContainer<T> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public T get(int key) {
        return (key == this.key) ? value : next.get(key);
    }

    public IntKeyContainer<T> put(int key, T elem) {
        if (key == this.key) {
            this.value = elem;
            return this;
        }
        this.next = next.put(key, elem);
        return this;
    }

    public IntKeyContainer<T> remove(int key) {
        if (key == this.key) {
            return next;
        }
        this.next = next.remove(key);
        return this;
    }

    public IntKeyContainer<T> clear() {
        return next;
    }

    public void apply(Functor<T> functor) {
        functor.apply(key, value);
        next.apply(functor);
    }

}
