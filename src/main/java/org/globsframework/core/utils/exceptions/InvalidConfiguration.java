package org.globsframework.core.utils.exceptions;

public class InvalidConfiguration extends GlobsException {
    public InvalidConfiguration(Exception e) {
        super(e);
    }

    public InvalidConfiguration(String message) {
        super(message);
    }

    public InvalidConfiguration(String message, Throwable cause) {
        super(message, cause);
    }
}
