package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValue;
import org.globsframework.model.impl.AbstractFieldValues;

public class ArrayFieldValues extends AbstractFieldValues {

  private FieldValue[] values;

  public ArrayFieldValues(FieldValue[] values) {
    this.values = values;
  }

  protected Object doGet(Field field) {
    for (FieldValue value : values) {
      if (value.getField().equals(field)) {
        return value.getValue();
      }
    }
    return null;
  }

  public boolean contains(Field field) {
    for (FieldValue value : values) {
      if (value.getField().equals(field)) {
        return true;
      }
    }
    return false;
  }

  public int size() {
    return values.length;
  }

  public void apply(Functor functor) throws Exception {
    for (FieldValue value : values) {
      functor.process(value.getField(), value.getValue());
    }
  }

  public FieldValue[] toArray() {
    return values;
  }
}
