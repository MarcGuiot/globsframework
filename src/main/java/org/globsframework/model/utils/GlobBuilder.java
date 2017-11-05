package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.*;
import org.globsframework.model.impl.DefaultGlob;
import org.globsframework.utils.exceptions.ItemNotFound;

public class GlobBuilder implements FieldValues.Functor, FieldSetter<GlobBuilder>, FieldValues {
    private FieldValuesBuilder fieldValuesBuilder = new FieldValuesBuilder();
    private GlobType globType;

    public static GlobBuilder init(GlobType globType) {
        return new GlobBuilder(globType);
    }

    public static Glob create(GlobType type, FieldValue... values) {
        return init(type, values).get();
    }

    public static GlobBuilder init(GlobType type, FieldValues values) {
        GlobBuilder builder = new GlobBuilder(type);
        values.safeApply(builder);
        return builder;
    }

    public static GlobBuilder init(GlobType type, FieldValue... values) {
        GlobBuilder builder = new GlobBuilder(type);
        FieldValues fieldValues = new ArrayFieldValues(values);
        fieldValues.safeApply(builder);
        return builder;
    }

    public static GlobBuilder init(Key key, FieldValue... values) {
        GlobBuilder builder = new GlobBuilder(key.getGlobType());
        key.safeApplyOnKeyField(builder);
        FieldValues fieldValues = new ArrayFieldValues(values);
        fieldValues.safeApply(builder);
        return builder;
    }

    public static GlobBuilder init(Key key, FieldValues values) {
        GlobBuilder builder = new GlobBuilder(key.getGlobType());
        key.safeApplyOnKeyField(builder);
        values.safeApply(builder);
        return builder;
    }

    public GlobBuilder set(DoubleField field, Double value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(DoubleField field, double value) throws ItemNotFound {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(IntegerField field, Integer value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(IntegerField field, int value) throws ItemNotFound {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(StringField field, String value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(BooleanField field, Boolean value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(LongField field, Long value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(LongField field, long value) throws ItemNotFound {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder set(BlobField field, byte[] value) {
        fieldValuesBuilder.set(field, value);
        return this;
    }

    public GlobBuilder setValue(Field field, Object value) throws ItemNotFound {
        fieldValuesBuilder.setValue(field, value);
        return this;
    }

    public GlobBuilder setObject(Field field, Object objectValue) {
        fieldValuesBuilder.setValue(field, objectValue);
        return this;
    }

    public Glob get() {
        return new DefaultGlob(globType, fieldValuesBuilder.get());
    }

    private GlobBuilder(GlobType globType) {
        this.globType = globType;
        for (Field field : globType.getFields()) {
            fieldValuesBuilder.setValue(field, field.getDefaultValue());
        }
    }

    public void process(Field field, Object value) throws Exception {
        setObject(field, value);
    }

    public boolean isNull(Field field) throws ItemNotFound {
        return getValue(field) == null;
    }

    public Object getValue(Field field) throws ItemNotFound {
        return fieldValuesBuilder.get().getValue(field);
    }

    public Double get(DoubleField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field, valueIfNull);
    }

    public Integer get(IntegerField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field, valueIfNull);
    }

    public String get(StringField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public Boolean get(BooleanField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public Boolean get(BooleanField field, boolean defaultIfNull) {
        return fieldValuesBuilder.get().get(field);
    }

    public boolean isTrue(BooleanField field) throws ItemNotFound {
        return fieldValuesBuilder.get().isTrue(field);
    }

    public Long get(LongField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public long get(LongField field, long valueIfNull) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field, valueIfNull);
    }

    public byte[] get(BlobField field) throws ItemNotFound {
        return fieldValuesBuilder.get().get(field);
    }

    public boolean contains(Field field) {
        return fieldValuesBuilder.get().contains(field);
    }

    public int size() {
        return fieldValuesBuilder.get().size();
    }

    public void apply(Functor functor) throws Exception {
        fieldValuesBuilder.get().apply(functor);
    }

    public void safeApply(Functor functor) {
        fieldValuesBuilder.get().safeApply(functor);
    }

    public FieldValue[] toArray() {
        return fieldValuesBuilder.get().toArray();
    }

    public String toString() {
        return fieldValuesBuilder.toString();
    }
}
