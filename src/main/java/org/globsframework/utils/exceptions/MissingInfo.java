package org.globsframework.utils.exceptions;

public class MissingInfo extends GlobsException {
    public MissingInfo(Exception e) {
        super(e);
    }

    public MissingInfo(String message) {
        super(message);
    }

    public MissingInfo(String message, Throwable cause) {
        super(message, cause);
    }
}
