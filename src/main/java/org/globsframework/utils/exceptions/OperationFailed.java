package org.globsframework.utils.exceptions;

public class OperationFailed extends GlobsException {
    public OperationFailed(Exception e) {
        super(e);
    }

    public OperationFailed(String message) {
        super(message);
    }

    public OperationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
