package org.globsframework.core.utils.exceptions;

public class ItemAlreadyExists extends GlobsException {
    public ItemAlreadyExists(Exception e) {
        super(e);
    }

    public ItemAlreadyExists(String message) {
        super(message);
    }

    public ItemAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
