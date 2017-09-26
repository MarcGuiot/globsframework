package org.globsframework.streams.accessors;

public interface IntegerAccessor extends Accessor {

  Integer getInteger();

  int getValue(int valueIfNull);

  boolean wasNull();
}
