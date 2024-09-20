package org.globsframework.core.streams.accessors;

public interface LongAccessor extends Accessor {

    Long getLong();

    long getValue(long valueIfNull);

    boolean wasNull();
}
