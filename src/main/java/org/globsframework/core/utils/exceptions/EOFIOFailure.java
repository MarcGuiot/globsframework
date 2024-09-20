package org.globsframework.core.utils.exceptions;

public class EOFIOFailure extends IOFailure {
    public EOFIOFailure(Exception e) {
        super(e);
    }

    public EOFIOFailure(String message) {
        super(message);
    }

    public EOFIOFailure(String message, Throwable cause) {
        super(message, cause);
    }
}
