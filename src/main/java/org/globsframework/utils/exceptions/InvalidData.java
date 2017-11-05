package org.globsframework.utils.exceptions;

public class InvalidData extends GlobsException {

    public InvalidData(String message) {
        super(message);
    }

    public InvalidData(Exception e) {
        super(e);
    }

    public InvalidData(String message, Exception cause) {
        super(message, cause);
    }
}
