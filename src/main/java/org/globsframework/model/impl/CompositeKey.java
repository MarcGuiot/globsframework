package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.AbstractKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.Key;
import org.globsframework.model.utils.FieldValueGetter;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class CompositeKey extends AbstractKey {
    private final GlobType type;
    private final Object[] values;
    private final int hashCode;

    public CompositeKey(GlobType type, FieldValueGetter getter) {
        this.type = type;
        Field[] keyFields = type.getKeyFields();
        this.values = new Object[keyFields.length];
        for (Field field : keyFields) {
            if (!getter.contains(field)) {
                throw new MissingInfo("Field '" + field.getName() +
                                      "' missing for identifying a Glob of type: " + type.getName());
            }
            values[field.getKeyIndex()] = getter.get(field);
        }
        hashCode = computeHash();
    }

    CompositeKey(GlobType type, Object[] globValues) {
        this.type = type;
        Field[] keyFields = type.getKeyFields();
        this.values = new Object[keyFields.length];
        int index = 0;
        for (Field field : keyFields) {
            values[field.getKeyIndex()] = globValues[field.getIndex()];
        }
        hashCode = computeHash();
    }

    public GlobType getGlobType() {
        return type;
    }

    public int size() {
        return type.getKeyFields().length;
    }

    public void apply(Functor functor) throws Exception {
        Field[] fields = type.getKeyFields();
        int index = 0;
        for (Field field : fields) {
            functor.process(field, values[field.getKeyIndex()]);
        }
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

    // optimized - do not use generated code
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (o.getClass().equals(CompositeKey.class)) {
            CompositeKey other = (CompositeKey)o;
            return type.equals(other.type) && Arrays.equals(values, other.values);
        }

        if (!AbstractKey.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        Key otherKey = (Key)o;
        if (!type.equals(otherKey.getGlobType())) {
            return false;
        }
        for (Field field : type.getKeyFields()) {
            if (!Utils.equal(getValue(field), otherKey.getValue(field))) {
                return false;
            }
        }
        return true;
    }

    // optimized - do not use generated code
    public int hashCode() {
        return hashCode;
    }

    private int computeHash() {
        int hashCode = type.hashCode();
        for (Field keyField : type.getKeyFields()) {
            Object value = getValue(keyField);
            hashCode = 31 * hashCode + (value != null ? value.hashCode() : 0);
        }
        if (hashCode == 0) {
            hashCode = 31;
        }
        return hashCode;
    }

    // Overwritten so that fields are always in the same order
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(type.getName());
        builder.append('[');
        Field[] fields = type.getKeyFields();
        int i = 0;
        for (Field field : fields) {
            builder.append(field.getName());
            builder.append('=');
            builder.append(getValue(field));
            i++;
            if (i < fields.length) {
                builder.append(',');
            }
        }
        builder.append(']');
        return builder.toString();
    }

    public FieldValue[] toArray() {
        Field[] keyFields = type.getKeyFields();
        FieldValue[] array = new FieldValue[keyFields.length];
        int index = 0;
        for (Field field : keyFields) {
            array[index] = new FieldValue(field, values[index]);
            index++;
        }
        return array;
    }

    protected Object getSwitchValue(Field field) {
        return values[field.getKeyIndex()];
    }
}
