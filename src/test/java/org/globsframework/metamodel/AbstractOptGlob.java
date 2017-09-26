package org.globsframework.metamodel;

import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.metamodel.fields.*;

public abstract class AbstractOptGlob extends AbstractKey implements Glob, MutableGlob {
  public abstract GlobType getType();

  public AbstractKey getKey() {
    return this;
  }

  public Key getTargetKey(Link link) {
    return null;
  }

  public boolean matches(FieldValues values) {
    return false;
  }

  public boolean matches(FieldValue... values) {
    return false;
  }

  public FieldValues getValues() {
    return null;
  }

  public boolean exists() {
    return false;
  }

  public Glob duplicate() {
    return null;
  }

  public abstract Object getValue(Field field) throws ItemNotFound;

  public Double get(DoubleField field) throws ItemNotFound {
    return (Double)getValue(field);
  }

  public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
    return (Double)getValue(field);
  }

  public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
    Object value = getValue(field);
    return value == null ? valueIfNull : ((Integer)value);
  }

  public Integer get(IntegerField field) throws ItemNotFound {
    return (Integer)getValue(field);
  }

   public String get(StringField field) throws ItemNotFound {
    return (String)getValue(field);
  }

  public Boolean get(BooleanField field) throws ItemNotFound {
    return (Boolean)getValue(field);
  }

  public Boolean get(BooleanField field, boolean defaultIfNull) {
    return (Boolean)getValue(field);
  }

  public boolean isTrue(BooleanField field) throws ItemNotFound {
    return get(field);
  }

  public Long get(LongField field) throws ItemNotFound {
    return (Long)getValue(field);
  }

  public byte[] get(BlobField field) throws ItemNotFound {
    return (byte[])getValue(field);
  }

  public boolean contains(Field field) {
    return field.getGlobType() == OptGlob.type;
  }

  public int size() {
    return getGlobType().getFieldCount();
  }

  public void apply(Functor functor) throws Exception {
    for (Field field : OptGlob.type.getFields()) {
      functor.process(field, getValue(field));
    }
  }

  public void safeApply(Functor functor) {
    try {
      for (Field field : OptGlob.type.getFields()) {
        functor.process(field, getValue(field));
      }
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public FieldValue[] toArray() {
    return new FieldValue[0];
  }

  public MutableGlob set(IntegerField field, Integer value) {
    setObject(field, value);
    return this;
  }

  public MutableGlob set(DoubleField field, Double value) {
    setObject(field, value);
    return this;
  }

  public MutableGlob set(StringField field, String value) {
    setObject(field, value);
    return this;
  }

  public MutableGlob set(BooleanField field, Boolean value) {
    return this;
  }

  public MutableGlob set(BlobField field, byte[] value) {
    return this;
  }

  public MutableGlob set(DoubleField field, double value) throws ItemNotFound {
    return this;
  }

  public MutableGlob set(IntegerField field, int value) throws ItemNotFound {
    return this;
  }

  public MutableGlob set(LongField field, long value) throws ItemNotFound {
    return this;
  }

  public MutableGlob setValue(Field field, Object value) {
    return this;
  }

  public abstract MutableGlob setObject(Field field, Object value);

  public MutableGlob setValues(FieldValues values) {
    return this;
  }

  public GlobType getGlobType() {
    return getType();
  }


  protected Object getSwitchValue(Field field) {
    return getValue(field);
  }
}
