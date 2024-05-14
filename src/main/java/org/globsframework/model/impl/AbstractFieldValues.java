package org.globsframework.model.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class AbstractFieldValues implements FieldValues {

    public Double get(DoubleField field) {
        return (Double)doCheckedGet(field);
    }

    public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
        Object o = doCheckedGet(field);
        if (o == null) {
            return valueIfNull;
        }
        return (Double)o;
    }

    public Integer get(IntegerField field) {
        return (Integer)doCheckedGet(field);
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        Integer value = (Integer)doCheckedGet(field);
        if (value == null) {
            return valueIfNull;
        }
        return value;
    }

    public String get(StringField field) {
        return (String)doCheckedGet(field);
    }

    public Boolean get(BooleanField field) {
        return (Boolean)doCheckedGet(field);
    }

    public boolean isTrue(BooleanField field) {
        return Boolean.TRUE.equals(doCheckedGet(field));
    }

    public boolean isNull(Field field) throws ItemNotFound {
        return doCheckedGet(field) == null;
    }

    public Object getValue(Field field) {
        return doCheckedGet(field);
    }

    public byte[] get(BlobField field) {
        return (byte[])doCheckedGet(field);
    }

    public boolean get(BooleanField field, boolean defaultIfNull) {
        Object value = doCheckedGet(field);
        return value == null ? Boolean.valueOf(defaultIfNull) : (boolean)value;
    }

    public Long get(LongField field) {
        return (Long)doCheckedGet(field);
    }

    public long get(LongField field, long valueIfNull) throws ItemNotFound {
        Object value = doCheckedGet(field);
        return value == null ? valueIfNull : (long)value;
    }

    public double[] get(DoubleArrayField field) throws ItemNotFound {
        return (double[])doCheckedGet(field);
    }

    public int[] get(IntegerArrayField field) throws ItemNotFound {
        return (int[])doCheckedGet(field);
    }

    public String[] get(StringArrayField field) throws ItemNotFound {
        return (String[])doCheckedGet(field);
    }

    public boolean[] get(BooleanArrayField field) {
        return (boolean[])doCheckedGet(field);
    }

    public long[] get(LongArrayField field) throws ItemNotFound {
        return (long[])doCheckedGet(field);
    }

    public LocalDate get(DateField field) throws ItemNotFound {
        return (LocalDate)doCheckedGet(field);
    }

    public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
        return (ZonedDateTime)doCheckedGet(field);
    }

    public BigDecimal get(BigDecimalField field) throws ItemNotFound {
        return (BigDecimal)doCheckedGet(field);
    }

    public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
        return (BigDecimal[])doCheckedGet(field);
    }

    public Glob get(GlobField field) throws ItemNotFound {
        return (Glob)doCheckedGet(field);
    }

    public Glob[] get(GlobArrayField field) throws ItemNotFound {
        return (Glob[])doCheckedGet(field);
    }

    public Glob get(GlobUnionField field) throws ItemNotFound {
        return (Glob)doCheckedGet(field);
    }

    public Glob[] get(GlobArrayUnionField field) throws ItemNotFound {
        return (Glob[])doCheckedGet(field);
    }

    protected abstract Object doCheckedGet(Field field);

}
