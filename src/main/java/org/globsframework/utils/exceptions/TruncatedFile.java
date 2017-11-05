package org.globsframework.utils.exceptions;

public class TruncatedFile extends GlobsException {
    public TruncatedFile(Exception e) {
        super(e);
    }

    public TruncatedFile() {
        super("");
    }

    public TruncatedFile(String message) {
        super(message);
    }

    public TruncatedFile(String message, Throwable cause) {
        super(message, cause);
    }
}