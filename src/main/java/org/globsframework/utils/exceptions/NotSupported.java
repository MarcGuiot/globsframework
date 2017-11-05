package org.globsframework.utils.exceptions;

public class NotSupported extends GlobsException {
    public NotSupported(Exception e) {
        super(e);
    }

    public NotSupported(String message) {
        super(message);
    }

    public NotSupported(String message, Throwable cause) {
        super(message, cause);
    }
}
