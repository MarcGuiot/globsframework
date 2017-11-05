package org.globsframework.utils.exceptions;

public class InvalidReference extends GlobsException {

    public InvalidReference(String message) {
        super(message);
    }

    public InvalidReference(Exception e) {
        super(e);
    }

    public InvalidReference(String message, Exception cause) {
        super(message, cause);
    }
}