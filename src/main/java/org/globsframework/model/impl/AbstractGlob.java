package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

import java.util.Objects;

public abstract class AbstractGlob extends AbstractFieldValues implements Glob, Key {
    protected int hashCode;

    public abstract GlobType getType();

    abstract public Object doGet(Field field);

    public String toString() {
        StringBuilder buffer = new StringBuilder(getType().getName()).append("[");
        GlobType type = getType();
        Field[] keyFields = type.getKeyFields();
        if (keyFields.length != 0) {
            for (int i = 0; i < keyFields.length; i++) {
                Field field = keyFields[i];
                buffer.append(field.getName()).append("=").append(doGet(field));
                if (i < keyFields.length - 1) {
                    buffer.append(", ");
                }
            }
        } else {
            Field[] fields = type.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                buffer.append(field.getName()).append("=").append(field.toString(doGet(field), ""));
                if (i < fields.length - 1) {
                    buffer.append(", ");
                }
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

    public <T extends FieldValueVisitor> T acceptOnKeyField(T functor) throws Exception {
        for (Field field : getType().getFields()) {
            field.visit(functor, doGet(field));
        }
        return functor;
    }

    public <T extends FieldValueVisitor> T safeAcceptOnKeyField(T functor) {
        try {
            for (Field field : getType().getKeyFields()) {
                field.visit(functor, doGet(field));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return functor;
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
        } catch (Exception e) {
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

            public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
                for (Field field : type.getKeyFields()) {
                    field.visit(functor, AbstractGlob.this.doGet(field));
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
        if (hashCode != 0) {
            return hashCode;
        }
        int hashCode = getType().hashCode();
        for (Field keyField : getType().getKeyFields()) {
            Object value = getValue(keyField);
            hashCode = 31 * hashCode + (value != null ? keyField.valueHash(value) : 0);
        }
        if (hashCode == 0) {
            hashCode = 31;
        }
        this.hashCode = hashCode;
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

        Key otherKey = (Key) o;
        if (!getType().equals(otherKey.getGlobType())) {
            return false;
        }

        Field[] keyFields = getType().getKeyFields();
        if (keyFields.length == 0) {
            return o instanceof Glob && reallyEquals((Glob) o);
        }

        for (Field field : keyFields) {
            if (!field.valueEqual(getValue(field), otherKey.getValue(field))) {
                return false;
            }
        }
        return true;
    }

    private boolean reallyEquals(Glob glob) {
        GlobType type = getType();
        for (Field field : type.getFields()) {
            if (!field.valueEqual(getValue(field), glob.getValue(field))) {
                return false;
            }
        }
        return true;
    }
}
