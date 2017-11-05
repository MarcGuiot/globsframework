package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.Accessor;

public class ValueAccessor implements Accessor {
    private Object value;

    public ValueAccessor(Object value) {
        this.value = value;
    }

    public ValueAccessor() {

    }

    public Object getObjectValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
