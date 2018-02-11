package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldValues;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class AbstractFieldValues implements FieldValues {
    public Double get(DoubleField field) {
        return (Double)doGet(field);
    }

    public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
        Object o = doGet(field);
        if (o == null) {
            return valueIfNull;
        }
        return (Double)o;
    }

    public Integer get(IntegerField field) {
        return (Integer)doGet(field);
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        Integer value = (Integer)doGet(field);
        if (value == null) {
            return valueIfNull;
        }
        return value;
    }

    public String get(StringField field) {
        return (String)doGet(field);
    }

    public Boolean get(BooleanField field) {
        return (Boolean)doGet(field);
    }

    public boolean isTrue(BooleanField field) {
        return Boolean.TRUE.equals(doGet(field));
    }

    public boolean isNull(Field field) throws ItemNotFound {
        return doGet(field) == null;
    }

    public Object getValue(Field field) {
        return doGet(field);
    }

    public byte[] get(BlobField field) {
        return (byte[])doGet(field);
    }

    public Boolean get(BooleanField field, boolean defaultIfNull) {
        Object value = doGet(field);
        return value == null ? Boolean.valueOf(defaultIfNull) : (Boolean)value;
    }

    public Long get(LongField field) {
        return (Long)doGet(field);
    }

    public long get(LongField field, long valueIfNull) throws ItemNotFound {
        Object value = doGet(field);
        return value == null ? valueIfNull : (long)value;
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

    public LocalDate get(DateField field) throws ItemNotFound {
        return (LocalDate)doGet(field);
    }

    public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
        return (ZonedDateTime)doGet(field);
    }

    public BigDecimal get(BigDecimalField field) throws ItemNotFound {
        return (BigDecimal)doGet(field);
    }

    public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
        return (BigDecimal[])doGet(field);
    }

    protected abstract Object doGet(Field field);

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
}
