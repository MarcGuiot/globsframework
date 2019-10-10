package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.DateAccessor;
import org.globsframework.streams.accessors.DateTimeAccessor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

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
