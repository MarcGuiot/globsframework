package org.globsframework.core.streams.accessors;

public interface DoubleAccessor extends Accessor {
    Double getDouble();

    double getValue(double valueIfNull);

    boolean wasNull();
}
