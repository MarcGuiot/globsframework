package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.utils.DefaultFieldValuesWithPrevious;

import java.time.ZonedDateTime;
import java.util.Date;

public class FieldValuesWithPreviousBuilder {
  public static FieldValuesWithPreviousBuilder init(GlobType type) {
    return new FieldValuesWithPreviousBuilder(type);
  }

  private DefaultFieldValuesWithPrevious values;

  public FieldValuesWithPreviousBuilder(GlobType type) {
    this.values = new DefaultFieldValuesWithPrevious(type);
  }

  public void completeForCreate() {
    this.values.completeForCreate();
  }

  public void completeForUpdate() {
    this.values.completeForUpdate();
  }

  public void completeForDelete() {
    this.values.completeForDelete();
  }

  public void setValue(Field field, Object value) {
    this.values.setValue(field, value);
  }

  public void setPreviousValue(Field field, Object value) {
    this.values.setPreviousValue(field, value);
  }

  public void setValue(Field field, Object value, Object previousValue) {
    this.values.setValue(field, value, previousValue);
  }

  public FieldValuesWithPrevious get() {
    return values;
  }

  public void set(IntegerField field, Integer newValue, Integer previousValue) {
    setValue(field, newValue, previousValue);
  }

  public void set(DoubleField field, Double newValue, Double previousValue) {
    setValue(field, newValue, previousValue);
  }

  public void set(StringField field, String newValue, String previousValue) {
    setValue(field, newValue, previousValue);
  }

  public void set(BooleanField field, Boolean newValue, Boolean previousValue) {
    setValue(field, newValue, previousValue);
  }

  public void set(BlobField field, byte[] newValue, byte[] previousValue) {
    setValue(field, newValue, previousValue);
  }

  public void set(LongField field, Long newValue, Long previousValue) {
    setValue(field, newValue, previousValue);
  }
}
