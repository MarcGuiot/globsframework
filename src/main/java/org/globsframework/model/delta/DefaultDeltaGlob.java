package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.*;
import org.globsframework.model.impl.AbstractFieldValuesWithPrevious;
import org.globsframework.utils.Unset;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

class DefaultDeltaGlob extends AbstractFieldValuesWithPrevious implements DeltaGlob {

  private Key key;
  private Object[] values;
  private Object[] previousValues;
  private DeltaState state = DeltaState.UNCHANGED;
  private DeltaFieldValuesFromArray previousFieldValue;

  public DefaultDeltaGlob(Key key) {
    this.key = key;
    int fieldCount = key.getGlobType().getFieldCount();
    this.values = new Object[fieldCount];
    this.previousValues = new Object[fieldCount];
    initWithKey(key);
    resetValues();
  }

  private void initWithKey(Key key) {
    key.safeApplyOnKeyField(new FieldValues.Functor() {
      public void process(Field field, Object value) throws Exception {
        values[field.getIndex()] = value;
        previousValues[field.getIndex()] = value;
      }
    });
  }

  private DefaultDeltaGlob() {
  }

  protected Object doGet(Field field) {
    checkFieldType(field);
    return values[field.getIndex()];
  }

  private void checkFieldType(Field field) {
    if (!field.getGlobType().equals(key.getGlobType())) {
      throw new ItemNotFound("Field '" + field.getName() + "' is declared for type '" +
                             field.getGlobType().getName() + "' and not for '" + key.getGlobType().getName() + "'");
    }
  }

  protected Object doGetPrevious(Field field) {
    checkFieldType(field);
    return previousValues[field.getIndex()];
  }

  public Key getKey() {
    return key;
  }

  public void setValue(Field field, Object value) {
    checkFieldType(field);
    values[field.getIndex()] = value;
  }

  public void setValueForUpdate(Field updatedField, Object value) {
    checkFieldType(updatedField);
    if (Utils.equal(value, previousValues[updatedField.getIndex()])) {
      previousValues[updatedField.getIndex()] = Unset.VALUE;
      values[updatedField.getIndex()] = Unset.VALUE;
      for (Field field : key.getGlobType().getFields()) {
        if (!field.isKeyField() && values[field.getIndex()] != Unset.VALUE) {
          return;
        }
      }
      state = DeltaState.UNCHANGED;
    }
    else {
      values[updatedField.getIndex()] = value;
    }
  }

  public void cleanupChanges() {
    boolean changed = false;
    for (Field field : key.getGlobType().getFields()) {
      if (field.isKeyField()) {
        continue;
      }
      if (Utils.equal(values[field.getIndex()], previousValues[field.getIndex()])) {
        previousValues[field.getIndex()] = Unset.VALUE;
        values[field.getIndex()] = Unset.VALUE;
      }
      else {
        changed = true;
      }
    }
    if (!changed) {
      state = DeltaState.UNCHANGED;
    }
  }

  public void setValue(Field field, Object value, Object previousValue) {
    checkFieldType(field);
    int index = field.getIndex();
    values[index] = value;
    previousValues[index] = previousValue;
  }

  public void setValues(FieldValues values) {
    values.safeApply(new FieldValues.Functor() {
      public void process(Field field, Object value) throws Exception {
        setValue(field, value);
      }
    });
  }

  public void setPreviousValues(FieldValues values) {
    values.safeApply(new FieldValues.Functor() {
      public void process(Field field, Object value) throws Exception {
        previousValues[field.getIndex()] = value;
      }
    });
  }

  public void mergePreviousValues(FieldValues globValues) {
    globValues.safeApply(new FieldValues.Functor() {
      public void process(Field field, Object value) throws Exception {
        final int index = field.getIndex();
        if (previousValues[index] == Unset.VALUE) {
          previousValues[index] = value;
        }
      }
    });
  }

  public FieldValues getValues() {
    return this;
  }

  public FieldValues getPreviousValues() {
    if (previousFieldValue == null) {
      previousFieldValue = new DeltaFieldValuesFromArray(key.getGlobType(), previousValues);
    }
    return previousFieldValue;
  }

  public boolean isModified() {
    return state != DeltaState.UNCHANGED;
  }

  public boolean isCreated() {
    return state == DeltaState.CREATED;
  }

  public boolean isDeleted() {
    return state == DeltaState.DELETED;
  }

  public boolean isUpdated() {
    return state == DeltaState.UPDATED;
  }

  public boolean isUpdated(Field field) {
    return (state == DeltaState.UPDATED) && (!Unset.VALUE.equals(values[field.getIndex()]));
  }

  public boolean isSet(Field field) {
    return doGet(field) != Unset.VALUE;
  }

  public void setState(DeltaState state) {
    this.state = state;
  }

  public void resetValues() {
    for (Field field : key.getGlobType().getFields()) {
      if (!field.isKeyField()) {
        values[field.getIndex()] = Unset.VALUE;
        previousValues[field.getIndex()] = Unset.VALUE;
      }
    }
  }

  public DeltaState getState() {
    return state;
  }

  public void processCreation(FieldValues values) {
    state.processCreation(this, values);
  }

  public void processUpdate(Field field, Object value, Object previousValue) {
    state.processUpdate(this, field, value, previousValue);
  }

  public void processDeletion(FieldValues values) {
    state.processDeletion(this, values);
  }

  public void visit(ChangeSetVisitor visitor) throws Exception {
    state.visit(this, visitor);
  }

  public void safeVisit(ChangeSetVisitor visitor) {
    try {
      state.visit(this, visitor);
    }
    catch (Exception e) {
      throw new UnexpectedApplicationState(e);
    }
  }

  public Object getValue(Field field) throws ItemNotFound {
    checkFieldType(field);
    Object value = values[field.getIndex()];
    if (value == Unset.VALUE) {
      throw new ItemNotFound(field.getName() + " not set.");
    }
    return value;
  }

  public boolean contains(Field field) {
    if (field.isKeyField()) {
      return false;
    }
    checkFieldType(field);
    return values[field.getIndex()] != Unset.VALUE;
  }

  public int size() {
    int count = -key.getGlobType().getKeyFields().length;
    for (Object value : values) {
      if (value != Unset.VALUE) {
        count++;
      }
    }
    return count;
  }

  public void apply(FieldValues.Functor functor) throws Exception {
    for (Field field : key.getGlobType().getFields()) {
      Object value = values[field.getIndex()];
      if ((value != Unset.VALUE) && !field.isKeyField()) {
        functor.process(field, value);
      }
    }
  }

  public void apply(FieldValuesWithPrevious.Functor functor) throws Exception {
    for (Field field : key.getGlobType().getFields()) {
      final int index = field.getIndex();
      Object value = values[index];
      if ((value != Unset.VALUE) && !field.isKeyField()) {
        functor.process(field, value, previousValues[index]);
      }
    }
  }

  public void applyOnPrevious(FieldValues.Functor functor) throws Exception {
    for (Field field : key.getGlobType().getFields()) {
      final int index = field.getIndex();
      Object value = previousValues[index];
      if ((value != Unset.VALUE) && !field.isKeyField()) {
        functor.process(field, value);
      }
    }
  }

  public FieldValue[] toArray() {
    FieldValue[] fieldValues = new FieldValue[size()];
    int i = 0;
    for (Field field : key.getGlobType().getFields()) {
      Object value = values[field.getIndex()];
      if (value != Unset.VALUE && !field.isKeyField()) {
        fieldValues[i] = new FieldValue(field, value);
        i++;
      }
    }
    return fieldValues;
  }

  public GlobType getType() {
    return key.getGlobType();
  }

  public DefaultDeltaGlob reverse() {
    DefaultDeltaGlob reverse = new DefaultDeltaGlob();
    reverse.key = this.key;
    reverse.state = this.state.reverse();

    reverse.values = new Object[this.previousValues.length];
    reverse.previousValues = new Object[this.values.length];

    for (Field field : key.getGlobType().getFields()) {
      int index = field.getIndex();
      if (field.isKeyField()) {
        reverse.values[index] = this.values[index];
        reverse.previousValues[index] = this.values[index];
      }
      else {
        reverse.values[index] = this.previousValues[index];
        reverse.previousValues[index] = this.values[index];
      }
    }

    return reverse;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(state).append("-").append(key).append(" => ");
    builder.append('[');
    Field[] fields = key.getGlobType().getFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      builder.append(field.getName());
      builder.append('=');
      builder.append(getValue(field));
      if (i < fields.length - 1) {
        builder.append(',');
      }
    }
    builder.append(']');
    return builder.toString();
  }

}
