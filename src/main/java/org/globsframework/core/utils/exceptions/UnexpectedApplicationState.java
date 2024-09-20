package org.globsframework.core.utils.exceptions;

public class UnexpectedApplicationState extends GlobsException {

    public UnexpectedApplicationState(Exception e) {
        super(e);
    }

    public UnexpectedApplicationState(String message) {
        super(message);
    }

    public UnexpectedApplicationState(String message, Exception e) {
        super(message, e);
    }
}
