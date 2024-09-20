package org.globsframework.core.utils.exceptions;

import org.globsframework.core.utils.Strings;

public class UnexpectedValue extends GlobsException {

    public UnexpectedValue(Object value) {
        super(Strings.toString(value));
    }

    public UnexpectedValue(Exception e) {
        super(e);
    }

    public UnexpectedValue(String message) {
        super(message);
    }

    public UnexpectedValue(String message, Throwable cause) {
        super(message, cause);
    }
}
