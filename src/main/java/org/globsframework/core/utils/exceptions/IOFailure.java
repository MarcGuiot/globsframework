package org.globsframework.core.utils.exceptions;

public class IOFailure extends GlobsException {
    public IOFailure(Exception e) {
        super(e);
    }

    public IOFailure(String message) {
        super(message);
    }

    public IOFailure(String message, Throwable cause) {
        super(message, cause);
    }
}
