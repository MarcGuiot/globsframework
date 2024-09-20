package org.globsframework.core.utils.exceptions;

public class InvalidFormat extends GlobsException {
    public InvalidFormat(Exception e) {
        super(e);
    }

    public InvalidFormat(String message) {
        super(message);
    }

    public InvalidFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
