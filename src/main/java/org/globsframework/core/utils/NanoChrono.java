package org.globsframework.core.utils;

public class NanoChrono {
    private long start;

    public NanoChrono() {
        start = System.nanoTime();
    }

    public static NanoChrono start() {
        return new NanoChrono();
    }

    public void reset() {
        start = System.nanoTime();
    }

    public double getAndResetElapsedTimeInMS() {
        long tmp = System.nanoTime();
        double current = ((int) ((tmp - start) / 1000.)) / 1000.;
        start = tmp;
        return current;
    }

    public double getElapsedTimeInMS() {
        return ((int) ((System.nanoTime() - start) / 1000.)) / 1000.;
    }
}
