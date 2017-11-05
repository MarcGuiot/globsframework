package org.globsframework.utils.exceptions;

public class ItemNotFound extends GlobsException {
    public ItemNotFound(Exception e) {
        super(e);
    }

    public ItemNotFound(String message) {
        super(message);
    }

    public ItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
