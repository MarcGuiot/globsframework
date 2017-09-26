package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.impl.AbstractFieldValuesWithPrevious;
import org.globsframework.utils.Unset;
import org.globsframework.utils.exceptions.ItemNotFound;

public class DefaultFieldValuesWithPrevious extends AbstractFieldValuesWithPrevious {

  private GlobType type;
  private Object[] values;
  private Object[] previousValues;

  public DefaultFieldValuesWithPrevious(GlobType type) {
    this.type = type;
    this.values = new Object[type.getFieldCount()];
    this.previousValues = new Object[type.getFieldCount()];
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      values[index] = Unset.VALUE;
      previousValues[index] = Unset.VALUE;
    }
  }

  public void setValue(Field field, Object value, Object previousValue) {
    int index = field.getIndex();
    values[index] = value;
    previousValues[index] = previousValue;
  }

  public void setValue(Field field, Object value) {
    values[field.getIndex()] = value;
  }

  public void setPreviousValue(Field field, Object previousValue) {
    previousValues[field.getIndex()] = previousValue;
  }

  public boolean contains(Field field) {
    return values[field.getIndex()] != Unset.VALUE;
  }

  public int size() {
    int count = 0;
    for (Object value : values) {
      if (value != Unset.VALUE) {
        count++;
      }
    }
    return count;
  }

  public void apply(FieldValuesWithPrevious.Functor functor) throws Exception {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (values[index] != Unset.VALUE) {
        functor.process(field, values[index], previousValues[index]);
      }
    }
  }

  public void applyOnPrevious(FieldValues.Functor functor) throws Exception {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (previousValues[index] != Unset.VALUE) {
        functor.process(field, previousValues[index]);
      }
    }
  }


  public FieldValues getPreviousValues() {
    return new GlobArrayFieldValues(type, previousValues);
  }

  public void apply(FieldValues.Functor functor) throws Exception {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (values[index] != Unset.VALUE) {
        functor.process(field, values[index]);
      }
    }
  }

  public FieldValue[] toArray() {
    FieldValue[] result = new FieldValue[size()];
    int resultIndex = 0;
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (values[index] != null) {
        result[resultIndex++] = new FieldValue(field, values[index]);
      }
    }
    return result;
  }

  protected Object doGet(Field field) {
    if (!field.getGlobType().equals(type)) {
      throw new ItemNotFound("Field '" + field.getName() + "' is declared for type '" +
                             field.getGlobType().getName() + "' and not for '" + type.getName() + "'");
    }
    return values[field.getIndex()];
  }

  protected Object doGetPrevious(Field field) {
    return previousValues[field.getIndex()];
  }

  public void completeForCreate() {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (values[index] == Unset.VALUE) {
        values[index] = null;
      }
      if (previousValues[index] == Unset.VALUE) {
        previousValues[index] = null;
      }
    }
  }

  public void completeForUpdate() {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if ((values[index] != Unset.VALUE) && (previousValues[index] == Unset.VALUE)) {
        previousValues[index] = field.getDefaultValue();
      }
    }
  }

  public void completeForDelete() {
    for (Field field : type.getFields()) {
      int index = field.getIndex();
      if (!field.isKeyField()) {
        values[index] = null;
        if (previousValues[index] == Unset.VALUE) {
          previousValues[index] = null;
        }
      }
    }
  }
}
