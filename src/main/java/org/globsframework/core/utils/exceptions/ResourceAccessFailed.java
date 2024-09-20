package org.globsframework.core.utils.exceptions;

public class ResourceAccessFailed extends GlobsException {
    public ResourceAccessFailed(Exception e) {
        super(e);
    }

    public ResourceAccessFailed(String message) {
        super(message);
    }

    public ResourceAccessFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
