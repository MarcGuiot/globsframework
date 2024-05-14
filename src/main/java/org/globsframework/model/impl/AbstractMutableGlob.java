package org.globsframework.model.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.model.utils.FieldCheck;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMutableGlob extends AbstractGlob implements MutableGlob  {

    public MutableGlob set(IntegerField field, Integer value) {
        return setObject(field, value);
    }

    public MutableGlob set(IntegerField field, int value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(IntegerArrayField field, int[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(DoubleField field, Double value) {
        return setObject(field, value);
    }

    public MutableGlob set(DoubleField field, double value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(DoubleArrayField field, double[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(LongField field, Long value) {
        return setObject(field, value);
    }

    public MutableGlob set(LongField field, long value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(LongArrayField field, long[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(StringField field, String value) {
        return setObject(field, value);
    }

    public MutableGlob set(StringArrayField field, String[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(BigDecimalField field, BigDecimal value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(BigDecimalArrayField field, BigDecimal[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(BooleanField field, Boolean value) {
        return setObject(field, value);
    }

    public MutableGlob set(BlobField field, byte[] value) {
        return setObject(field, value);
    }

    public MutableGlob setValue(Field field, Object value) {
        return setObject(field, value);
    }

    public MutableGlob set(BooleanArrayField field, boolean[] value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(DateField field, LocalDate value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(DateTimeField field, ZonedDateTime value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(GlobField field, Glob value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(GlobArrayField field, Glob[] values) throws ItemNotFound {
        return setObject(field, values);
    }

    public MutableGlob set(GlobUnionField field, Glob value) throws ItemNotFound {
        return setObject(field, value);
    }

    public MutableGlob set(GlobArrayUnionField field, Glob[] values) throws ItemNotFound {
        return setObject(field, values);
    }

    public MutableGlob setValues(FieldValues values) {
        values.safeApply(this::setObject);
        return this;
    }

    public Key getTargetKey(Link link) {
        if (!link.getSourceType().equals(getType())) {
            throw new InvalidParameter("Link '" + link + " cannot be used with " + this);
        }

        KeyBuilder keyBuilder = KeyBuilder.init(link.getTargetType());
        link.apply((sourceField, targetField) -> {
            Object value = getValue(sourceField);
            keyBuilder.setObject(targetField, value);

        });
        return keyBuilder.get();
    }

    public FieldValues getValues() {
        FieldValuesBuilder builder = FieldValuesBuilder.init();
        for (Field field : getType().getFields()) {
            if (!field.isKeyField()) {
                builder.setValue(field, doGet(field));
            }
        }
        return builder.get();
    }

    public FieldValue[] toArray() {
        List<FieldValue> fieldValueList = new ArrayList<>();
        for (Field field : getType().getFields()) {
            if (isSet(field)) {
                fieldValueList.add(new FieldValue(field, doGet(field)));
            }
        }
        return fieldValueList.toArray(FieldValue[]::new);
    }

    public Object doCheckedGet(Field field) {
        FieldCheck.check(field, getType());
        return doGet(field);
    }

    public MutableGlob setObject(Field field, Object value){
        if (FieldCheck.CheckGlob.shouldCheck) {
            if (isHashComputed() && field.isKeyField()) {
                throw new RuntimeException(field.getFullName() + " is a key value and the hashCode is already computed.");
            }
            FieldCheck.check(field, getType(), value);
        }
        return doSet(field, value);
    }

    abstract public boolean isHashComputed();

    abstract public MutableGlob doSet(Field field, Object value);

    public Key getKey() {
        return this;
    }

}
