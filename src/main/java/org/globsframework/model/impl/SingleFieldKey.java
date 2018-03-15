package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.*;
import org.globsframework.model.utils.FieldCheck;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class SingleFieldKey extends AbstractKey {
    private final Field keyField;
    private Object value;
    private int hashCode;

    public SingleFieldKey(Field keyField) {
        this.keyField = keyField;
    }

    public SingleFieldKey(Field field, Object value) throws MissingInfo {
        FieldCheck.checkIsKeyOf(field, field.getGlobType(), value);
        this.keyField = field;
        this.value = value;
        hashCode = computeHash();
    }

    public SingleFieldKey(GlobType type, Object value) throws InvalidParameter {
        this(getKeyField(type), value);
    }

    private static Field getKeyField(GlobType type) throws InvalidParameter {
        Field[] keyFields = type.getKeyFields();
        if (keyFields.length != 1) {
            throw new InvalidParameter("Cannot use a single field key for type " + type + " - " +
                                       "key fields=" + Arrays.toString(keyFields));
        }
        return keyFields[0];
    }

    public GlobType getGlobType() {
        return keyField.getGlobType();
    }

    public <T extends FieldValues.Functor>
    T  apply(T functor) throws Exception {
        functor.process(keyField, value);
        return functor;
    }

    public <T extends FieldValues.Functor>
    T safeApply(T functor) {
        try {
            functor.process(keyField, value);
            return functor;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return 1;
    }

    // optimized - do not use generated code
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass().equals(SingleFieldKey.class)) {
            SingleFieldKey otherSingleFieldKey = (SingleFieldKey)o;
            return otherSingleFieldKey.keyField == keyField &&
                   keyField.valueEqual(otherSingleFieldKey.value, value);
        }

        if (!Key.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        Key otherKey = (Key)o;
        return keyField.getGlobType() == otherKey.getGlobType()
               && keyField.valueEqual(value, otherKey.getValue(keyField));
    }

    // optimized - do not use generated code
    public int hashCode() {
        if (hashCode != 0){
            return hashCode;
        }
        return hashCode = computeHash();
    }

    private int computeHash() {
        int hash = getGlobType().hashCode();
        hash = 31 * hash + (value != null ? keyField.valueHash(value) : 0);
        if (hash == 0) {
            hash = 31;
        }
        return hash;
    }

    public FieldValue[] toArray() {
        return new FieldValue[]{
            new FieldValue(keyField, value),
            };
    }

    public String toString() {
        return getGlobType().getName() + "[" + keyField.getName() + "=" + value + "]";
    }

    protected Object doGetValue(Field field) {
        if (field.getKeyIndex() == 0) {
            return value;
        }
        throw new InvalidParameter(field + " is not a key field");
    }

    public MutableKey setValue(Field field, Object value) throws ItemNotFound {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        this.value = value;
        return this;
    }

    public void reset() {
        this.value = null;
        this.hashCode = 0;
    }

    public MutableKey duplicateKey() {
        return new SingleFieldKey(keyField, value);
    }
}
