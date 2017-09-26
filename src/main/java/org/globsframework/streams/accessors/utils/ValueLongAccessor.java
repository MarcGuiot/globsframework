package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.LongAccessor;

public class ValueLongAccessor implements LongAccessor {
  private Long value;

  public ValueLongAccessor(Long value) {
    this.value = value;
  }

  public ValueLongAccessor() {
  }

  public Long getLong() {
    return value;
  }

  public long getValue(long valueIfNull) {
    return value == null ? valueIfNull : value;
  }

   public boolean wasNull() {
      return value == null;
   }

   public Object getObjectValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
