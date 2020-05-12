package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.StringArrayAccessor;

public class ValueStringArrayAccessor implements StringArrayAccessor {
    private String[] value;

    public ValueStringArrayAccessor(String[] value) {
        this.value = value;
    }

    public ValueStringArrayAccessor() {
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String[] getString() {
        return value;
    }

    public Object getObjectValue() {
        return value;
    }
}
