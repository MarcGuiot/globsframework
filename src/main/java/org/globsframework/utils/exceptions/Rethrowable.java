package org.globsframework.utils.exceptions;

public class Rethrowable extends GlobsException {
    public Rethrowable(Exception e) {
        super(e);
    }

    public <E> E getException() {
        return (E)getCause();
    }
}
