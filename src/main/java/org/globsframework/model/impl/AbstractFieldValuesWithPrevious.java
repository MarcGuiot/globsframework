package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.time.ZonedDateTime;
import java.util.Date;

public abstract class AbstractFieldValuesWithPrevious implements FieldValuesWithPrevious {
  protected abstract Object doGet(Field field);

  protected abstract Object doGetPrevious(Field field);

   public boolean isNull(Field field) throws ItemNotFound {
      return doGet(field) == null;
   }

   public Object getValue(Field field) throws ItemNotFound {
    return doGet(field);
  }

  public Double get(DoubleField field) throws ItemNotFound {
    return (Double)doGet(field);
  }

  public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
    Object value = doGet(field);
    if (value == null){
      return valueIfNull;
    }
    return (Double)value;
  }

  public Integer get(IntegerField field) throws ItemNotFound {
    return (Integer)doGet(field);
  }

  public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
    Integer value = get(field);
    return value == null ? valueIfNull : value;
  }

   public String get(StringField field) throws ItemNotFound {
    return (String)doGet(field);
  }

  public Boolean get(BooleanField field) throws ItemNotFound {
    return (Boolean)doGet(field);
  }

  public Boolean get(BooleanField field, boolean defaultIfNull) {
    return (Boolean)doGet(field);
  }

  public boolean isTrue(BooleanField field) {
    return Boolean.TRUE.equals(get(field));
  }

  public Long get(LongField field) throws ItemNotFound {
    return (Long)doGet(field);
  }

  public long get(LongField field, long valueIfNull) throws ItemNotFound {
    Long ret = (Long)doGet(field);
    return ret == null ? valueIfNull : ret;
  }

  public byte[] get(BlobField field) throws ItemNotFound {
    return (byte[])doGet(field);
  }

  public Object getPreviousValue(Field field) throws ItemNotFound {
    return doGetPrevious(field);
  }

  public Double getPrevious(DoubleField field) throws ItemNotFound {
    return (Double)doGetPrevious(field);
  }

  public double getPrevious(DoubleField field, double defaultIfNull) throws ItemNotFound {
    Double previous = getPrevious(field);
    if (previous == null) {
      return defaultIfNull;
    }
    return previous;
  }

  public long getPrevious(LongField field, long valueIfNull) throws ItemNotFound {
    Object previous = doGetPrevious(field);
    return previous == null ? valueIfNull : (long)previous;
  }

  public Integer getPrevious(IntegerField field) throws ItemNotFound {
    return (Integer)doGetPrevious(field);
  }

   public String getPrevious(StringField field) throws ItemNotFound {
    return (String)doGetPrevious(field);
  }

  public Boolean getPrevious(BooleanField field) throws ItemNotFound {
    return (Boolean)doGetPrevious(field);
  }

  public Boolean getPrevious(BooleanField field, boolean defaultIfNull) {
    return (Boolean)doGetPrevious(field);
  }

  public Long getPrevious(LongField field) throws ItemNotFound {
    return (Long)doGetPrevious(field);
  }

  public byte[] getPrevious(BlobField field) throws ItemNotFound {
    return (byte[])doGetPrevious(field);
  }

  public void safeApply(FieldValues.Functor functor) {
    try {
      apply(functor);
    }
    catch (RuntimeException e) {
      throw e;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void safeApply(FieldValuesWithPrevious.Functor functor) {
    try {
      apply(functor);
    }
    catch (RuntimeException e) {
      throw e;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void safeApplyOnPrevious(FieldValues.Functor functor) {
     try {
       applyOnPrevious(functor);
     }
     catch (RuntimeException e) {
       throw e;
     }
     catch (Exception e) {
       throw new RuntimeException(e);
     }
   }
}
