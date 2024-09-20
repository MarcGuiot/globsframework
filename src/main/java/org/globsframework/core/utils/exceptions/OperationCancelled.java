package org.globsframework.core.utils.exceptions;

public class OperationCancelled extends GlobsException {

    public OperationCancelled(Exception e) {
        super(e);
    }

    public OperationCancelled(String message) {
        super(message);
    }

    public OperationCancelled(String message, Throwable cause) {
        super(message, cause);
    }
}
