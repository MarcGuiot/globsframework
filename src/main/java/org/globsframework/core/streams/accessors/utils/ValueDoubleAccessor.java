package org.globsframework.core.streams.accessors.utils;

import org.globsframework.core.streams.accessors.DoubleAccessor;

public class ValueDoubleAccessor implements DoubleAccessor {
    private Double value;

    public ValueDoubleAccessor() {
    }

    public ValueDoubleAccessor(Double value) {
        this.value = value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getDouble() {
        return value;
    }

    public double getValue(double valueIfNull) {
        return value == null ? valueIfNull : value;
    }

    public boolean wasNull() {
        return value == null;
    }

    public Object getObjectValue() {
        return value;
    }
}
