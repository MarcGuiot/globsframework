package org.globsframework.utils;

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

    public double getElapsedTimeInMS() {
        return ((int) ((System.nanoTime() - start) / 1000.)) / 1000.;
    }
}
