package org.globsframework.core.utils.exceptions;

public class TooManyItems extends GlobsException {
    public TooManyItems(Exception e) {
        super(e);
    }

    public TooManyItems(String message) {
        super(message);
    }

    public TooManyItems(String message, Throwable cause) {
        super(message, cause);
    }
}
