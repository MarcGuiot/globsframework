package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.DateTimeAccessor;

import java.time.ZonedDateTime;

public class ValueDateTimeAccessor implements DateTimeAccessor {
    private ZonedDateTime value;

    public ValueDateTimeAccessor(ZonedDateTime value) {
        this.value = value;
    }

    public ValueDateTimeAccessor() {
    }

    public ZonedDateTime getDateTime() {
        return value;
    }

    public boolean wasNull() {
        return value == null;
    }

    public Object getObjectValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }
}
