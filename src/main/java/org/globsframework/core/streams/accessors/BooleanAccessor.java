package org.globsframework.core.streams.accessors;

public interface BooleanAccessor extends Accessor {

    Boolean getBoolean();

    boolean getValue(boolean valueIfNull);
}
