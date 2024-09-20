package org.globsframework.core.streams.accessors.utils;

import org.globsframework.core.streams.accessors.DateAccessor;

import java.time.LocalDate;

public class ValueDateAccessor implements DateAccessor {
    private LocalDate value;

    public ValueDateAccessor(LocalDate value) {
        this.value = value;
    }

    public ValueDateAccessor() {
    }

    public LocalDate getDate() {
        return value;
    }

    public boolean wasNull() {
        return value == null;
    }

    public Object getObjectValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
