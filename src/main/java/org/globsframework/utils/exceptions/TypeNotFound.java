package org.globsframework.utils.exceptions;

public class TypeNotFound extends RuntimeException {
    public TypeNotFound(String name) {
        super("type '" + name + "' not found.");
    }
}
