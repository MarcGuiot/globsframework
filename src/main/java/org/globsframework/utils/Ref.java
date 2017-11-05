package org.globsframework.utils;

public final class Ref<T> {
    private T ref;

    public Ref() {
    }

    public Ref(T ref) {
        this.ref = ref;
    }

    public T get() {
        return ref;
    }

    public void set(T ref) {
        this.ref = ref;
    }

    public int hashCode() {
        return ref != null ? super.hashCode() : 0;
    }

    public boolean equals(Object object) {
        return ref != null ? ref.equals(object) : (object == null);
    }

    public String toString() {
        return ref != null ? ref.toString() : null;
    }
}
