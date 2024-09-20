package org.globsframework.core.streams.accessors.utils;

import org.globsframework.core.streams.accessors.BooleanAccessor;

public class ValueBooleanAccessor implements BooleanAccessor {
    private Boolean value;

    public ValueBooleanAccessor() {
    }

    public ValueBooleanAccessor(Boolean value) {
        this.value = value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Object getObjectValue() {
        return value;
    }

    public Boolean getBoolean() {
        return value;
    }

    public boolean getValue(boolean valueIfNull) {
        return value;
    }
}
