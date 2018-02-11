package org.globsframework.functional.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldSetter;
import org.globsframework.model.FieldValues;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class AbstractFieldValue<T extends FieldSetter> implements FieldSetter<T>, FieldValues {

    protected abstract T doSet(Field field, Object o);

    public T set(DoubleField doubleField, Double aDouble) throws ItemNotFound {
        return doSet(doubleField, aDouble);
    }

    public T set(DoubleField doubleField, double v) throws ItemNotFound {
        return doSet(doubleField, v);
    }

    public T set(IntegerField field, Integer value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(IntegerField field, int i) throws ItemNotFound {
        return doSet(field, i);
    }

    public T set(StringField field, String s) throws ItemNotFound {
        return doSet(field, s);
    }

    public T set(BooleanField field, Boolean aBoolean) throws ItemNotFound {
        return doSet(field, aBoolean);
    }

    public T set(LongField field, Long aLong) throws ItemNotFound {
        return doSet(field, aLong);
    }

    public T set(LongArrayField field, long[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(BigDecimalField field, BigDecimal value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(BigDecimalArrayField field, BigDecimal[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(LongField field, long l) throws ItemNotFound {
        return doSet(field, l);
    }

    public T set(BlobField blobField, byte[] bytes) throws ItemNotFound {
        return doSet(blobField, bytes);
    }

    public T set(DoubleArrayField field, double[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(IntegerArrayField field, int[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(StringArrayField field, String[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(BooleanArrayField field, boolean[] value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(DateField field, LocalDate value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T set(DateTimeField field, ZonedDateTime value) throws ItemNotFound {
        return doSet(field, value);
    }

    public T setValue(Field field, Object o) throws ItemNotFound {
        return doSet(field, o);
    }

    public void safeApply(Functor functor) {
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

    protected abstract Object doGet(Field field);

    public boolean isNull(Field field) throws ItemNotFound {
        return doGet(field) != null;
    }

    public Object getValue(Field field) throws ItemNotFound {
        return doGet(field);
    }

    public Double get(DoubleField doubleField) throws ItemNotFound {
        return (Double)getValue(doubleField);
    }

    public double get(DoubleField doubleField, double v) throws ItemNotFound {
        Double aDouble = get(doubleField);
        return aDouble != null ? aDouble : v;
    }

    public Integer get(IntegerField field) throws ItemNotFound {
        return (Integer)doGet(field);
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        Integer realValue = get(field);
        return realValue == null ? valueIfNull : realValue;
    }

    public String get(StringField field) throws ItemNotFound {
        return (String)doGet(field);
    }

    public Boolean get(BooleanField field) throws ItemNotFound {
        return (Boolean)doGet(field);
    }

    public Boolean get(BooleanField booleanField, boolean valueIfNull) {
        Boolean realValue = get(booleanField);
        return realValue != null ? realValue : valueIfNull;
    }

    public boolean isTrue(BooleanField booleanField) throws ItemNotFound {
        return get(booleanField, false);
    }

    public Long get(LongField field) throws ItemNotFound {
        return (Long)doGet(field);
    }

    public long get(LongField longField, long valueIfNull) throws ItemNotFound {
        Long realValue = get(longField);
        return realValue != null ? realValue : valueIfNull;
    }

    public byte[] get(BlobField blobField) throws ItemNotFound {
        return (byte[])doGet(blobField);
    }

    public double[] get(DoubleArrayField field) throws ItemNotFound {
        return (double[])doGet(field);
    }

    public int[] get(IntegerArrayField field) throws ItemNotFound {
        return (int[])doGet(field);
    }

    public String[] get(StringArrayField field) throws ItemNotFound {
        return (String[])doGet(field);
    }

    public boolean[] get(BooleanArrayField field) {
        return (boolean[])doGet(field);
    }

    public long[] get(LongArrayField field) throws ItemNotFound {
        return (long[])doGet(field);
    }

    public BigDecimal get(BigDecimalField field) throws ItemNotFound {
        return (BigDecimal)doGet(field);
    }

    public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
        return (BigDecimal[])doGet(field);
    }

    public LocalDate get(DateField field) throws ItemNotFound {
        return (LocalDate)doGet(field);
    }

    public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
        return (ZonedDateTime)doGet(field);
    }
}
