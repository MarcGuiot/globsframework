package org.globsframework.streams.accessors;

public interface LongAccessor extends Accessor {

    Long getLong();

    long getValue(long valueIfNull);

    boolean wasNull();
}
