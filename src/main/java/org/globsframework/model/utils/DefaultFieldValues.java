package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldSetter;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.MutableFieldValues;
import org.globsframework.model.impl.AbstractFieldValues;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultFieldValues extends AbstractFieldValues implements MutableFieldValues {
    private Map<Field, Object> values = new HashMap<Field, Object>();

    public DefaultFieldValues() {
    }

    public DefaultFieldValues(FieldValues newValues) {
        FieldValue[] values = newValues.toArray();
        for (FieldValue fieldValue : values) {
            this.values.put(fieldValue.getField(), fieldValue.getValue());
        }
    }

    public boolean contains(Field field) {
        return values.containsKey(field);
    }

    protected Object doGet(Field field) {
        return values.get(field);
    }

    public int size() {
        return values.size();
    }

    public void apply(Functor functor) throws Exception {
        for (Map.Entry<Field, Object> entry : values.entrySet()) {
            functor.process(entry.getKey(), entry.getValue());
        }
    }

    public DefaultFieldValues setValue(Field field, Object value) throws InvalidParameter {
        field.checkValue(value);
        values.put(field, value);
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (Iterator<Map.Entry<Field, Object>> iterator = values.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Field, Object> entry = iterator.next();
            Field field = entry.getKey();
            builder.append(field.getName());
            builder.append('=');
            builder.append(getValue(field));
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }
        builder.append(']');
        return builder.toString();
    }

    public DefaultFieldValues set(IntegerField field, Integer value) {
        setValue(field, value);
        return this;
    }

    public DefaultFieldValues set(DoubleField field, Double value) {
        setValue(field, value);
        return this;
    }

    public DefaultFieldValues set(StringField field, String value) {
        setValue(field, value);
        return this;
    }

    public DefaultFieldValues set(BooleanField field, Boolean value) {
        setValue(field, value);
        return this;
    }

    public DefaultFieldValues set(LongField field, Long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public DefaultFieldValues set(BlobField field, byte[] value) {
        setValue(field, value);
        return this;
    }

    public FieldSetter set(DoubleField field, double value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public FieldSetter set(IntegerField field, int value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public FieldSetter set(LongField field, long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public void setValues(FieldValues values) {
        values.safeApply(new FieldValues.Functor() {
            public void process(Field field, Object value) throws IOException {
                setValue(field, value);
            }
        });
    }

    public void remove(Field field) {
        values.remove(field);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DefaultFieldValues that = (DefaultFieldValues)o;

        if (values != null ? !values.equals(that.values) : that.values != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return (values != null ? values.hashCode() : 0);
    }

    public FieldValue[] toArray() {
        FieldValue[] array = new FieldValue[values.size()];
        int index = 0;
        for (Map.Entry<Field, Object> entry : values.entrySet()) {
            array[index] = new FieldValue(entry.getKey(), entry.getValue());
            index++;
        }
        return array;
    }
}
