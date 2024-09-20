package org.globsframework.core.streams.accessors.utils;

import org.globsframework.core.streams.accessors.LongArrayAccessor;

public class ValueLongArrayAccessor implements LongArrayAccessor {
    private long[] value;

    public ValueLongArrayAccessor(long[] value) {
        this.value = value;
    }

    public ValueLongArrayAccessor() {
    }

    public void setValue(long[] value) {
        this.value = value;
    }

    public Object getObjectValue() {
        return value;
    }

    public long[] getValues() {
        return value;
    }
}
