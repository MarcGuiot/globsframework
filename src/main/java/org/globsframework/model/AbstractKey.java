package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.utils.FieldCheck;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class AbstractKey implements Key, MutableKey, FieldValues {

    public Boolean get(BooleanField field, boolean defaultIfNull) {
        Boolean value = get(field);
        if (value == null) {
            return defaultIfNull;
        }
        return value;
    }

    public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
        Double value = get(field);
        if (value == null) {
            return valueIfNull;
        }
        return value;
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        Integer value = get(field);
        return value == null ? valueIfNull : value;
    }

    public byte[] get(BlobField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (byte[]) doGetValue(field);
    }

    public Boolean get(BooleanField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (Boolean) doGetValue(field);
    }

    public Double get(DoubleField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (Double) doGetValue(field);
    }

    public double[] get(DoubleArrayField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (double[]) doGetValue(field);
    }

    public int[] get(IntegerArrayField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (int[]) doGetValue(field);
    }

    public String[] get(StringArrayField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (String[]) doGetValue(field);
    }

    public boolean[] get(BooleanArrayField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (boolean[]) doGetValue(field);
    }

    public long[] get(LongArrayField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (long[]) doGetValue(field);
    }

    public LocalDate get(DateField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (LocalDate) doGetValue(field);
    }

    public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (ZonedDateTime) doGetValue(field);
    }

    public BigDecimal get(BigDecimalField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (BigDecimal) doGetValue(field);
    }

    public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (BigDecimal[]) doGetValue(field);
    }

    public boolean isNull(Field field) throws ItemNotFound {
        return getValue(field) == null;
    }

    public Object getValue(Field field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return doGetValue(field);
    }

    public Integer get(IntegerField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (Integer) doGetValue(field);
    }

    public Long get(LongField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (Long) doGetValue(field);
    }

    public long get(LongField field, long valueIfNull) throws ItemNotFound {
        Long value = get(field);
        return value == null ? valueIfNull : value;
    }

    public String get(StringField field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return (String) doGetValue(field);
    }

    public boolean isTrue(BooleanField field) {
        return get(field);
    }

    public boolean contains(Field field) {
        return field.getGlobType() == getGlobType() && field.isKeyField();
    }

    public <T extends Functor>
    T applyOnKeyField(T functor) throws Exception {
        apply(functor);
        return functor;
    }

    public <T extends Functor>
    T safeApplyOnKeyField(T functor) {
        return safeApply(functor);
    }

    public FieldValues asFieldValues() {
        return this;
    }

    protected abstract Object doGetValue(Field field);

    public MutableKey set(DoubleField field, Double value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DoubleField field, double value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DoubleArrayField field, double[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerField field, Integer value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerField field, int value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerArrayField field, int[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(StringField field, String value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(StringArrayField field, String[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BooleanField field, Boolean value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BooleanArrayField field, boolean[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongField field, Long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongArrayField field, long[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongField field, long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BigDecimalField field, BigDecimal value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BigDecimalArrayField field, BigDecimal[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BlobField field, byte[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DateField field, LocalDate value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DateTimeField field, ZonedDateTime value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }
}
