package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

import java.util.Objects;

public abstract class AbstractGlob extends AbstractFieldValues implements Glob, Key {
    public abstract GlobType getType();

    abstract public Object doGet(Field field);

    public String toString() {
        StringBuilder buffer = new StringBuilder(getType().getName()).append("[");
        GlobType type = getType();
        Field[] keyFields = type.getKeyFields();
        for (int i = 0; i < keyFields.length; i++) {
            Field field = keyFields[i];
            buffer.append(field.getName()).append("=").append(doGet(field));
            if (i < keyFields.length - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public final boolean matches(FieldValues values) {
        return values.safeApply(new Functor() {
            Boolean result = Boolean.TRUE;

            public void process(Field field, Object value) {
                if (!Objects.equals(value, getValue(field))) {
                    result = Boolean.FALSE;
                }
            }
        }).result;
    }

    public boolean matches(FieldValue... values) {
        for (FieldValue value : values) {
            if (!Objects.equals(value.getValue(), getValue(value.getField()))) {
                return false;
            }
        }
        return true;
    }

    public <T extends FieldValues.Functor>
    T applyOnKeyField(T functor) throws Exception {
        for (Field field : getType().getFields()) {
            functor.process(field, doGet(field));
        }
        return functor;
    }

    public <T extends FieldValues.Functor>
    T safeApplyOnKeyField(T functor) {
        try {
            for (Field field : getType().getKeyFields()) {
                functor.process(field, doGet(field));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return functor;
    }

    // implement asFieldValues for key
    public FieldValues asFieldValues() {
        return new AbstractFieldValues() {
            GlobType type = getType();

            public boolean contains(Field field) {
                return field.isKeyField();
            }

            public int size() {
                return type.getKeyFields().length;
            }

            public <T extends Functor>
            T apply(T functor) throws Exception {
                for (Field field : type.getKeyFields()) {
                    functor.process(field, AbstractGlob.this.doGet(field));
                }
                return functor;
            }

            public FieldValue[] toArray() {
                FieldValue[] arrays = new FieldValue[type.getKeyFields().length];
                int i = 0;
                for (Field field : type.getKeyFields()) {
                    arrays[i] = new FieldValue(field, AbstractGlob.this.doGet(field));
                    i++;
                }
                return arrays;
            }

            public Object doCheckedGet(Field field) {
                return AbstractGlob.this.doGet(field);
            }

        };
    }

    public boolean contains(Field field) {
        return field.getGlobType().equals(getType());
    }

    public int size() {
        return getType().getFieldCount();
    }

    public GlobType getGlobType() {
        return getType();
    }

    public int hashCode() {
        int hashCode = getType().hashCode();
        for (Field keyField : getType().getKeyFields()) {
            Object value = getValue(keyField);
            hashCode = 31 * hashCode + (value != null ? keyField.valueHash(value) : 0);
        }
        if (hashCode == 0) {
            hashCode = 31;
        }
        return hashCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (!Key.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        Key otherKey = (Key)o;
        if (!getType().equals(otherKey.getGlobType())) {
            return false;
        }

        for (Field field : getType().getKeyFields()) {
            if (!field.valueEqual(getValue(field), otherKey.getValue(field))) {
                return false;
            }
        }
        return true;
    }
}
