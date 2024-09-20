package org.globsframework.core.utils;

public class RefCount<T> {
    public final T ref;
    private int count;

    public RefCount(T ref) {
        this.ref = ref;
    }

    synchronized public boolean dec() {
        count--;
        if (count <= 0) {
            count = 0;
            return true;
        }
        return false;
    }

    synchronized public void inc() {
        count++;
    }

    public synchronized boolean isUsed() {
        return count != 0;
    }
}
