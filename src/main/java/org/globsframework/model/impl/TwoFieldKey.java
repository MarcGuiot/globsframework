package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.AbstractKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class TwoFieldKey extends AbstractKey {
    private final GlobType type;
    private final Object value1;
    private final Object value2;
    private final int hashCode;

    public TwoFieldKey(Field keyField1, Object value1,
                       Field keyField2, Object value2) throws MissingInfo {

        SingleFieldKey.checkValue(keyField1, value1);
        SingleFieldKey.checkValue(keyField2, value2);
        type = keyField1.getGlobType();
        Field[] keyFields = keyField1.getGlobType().getKeyFields();
        if (keyFields.length != 2) {
            throw new InvalidParameter("Cannot use a two-fields key for type " + keyField1.getGlobType() + " - " +
                                       "key fields=" + Arrays.toString(keyFields));
        }
        Field field;
        field = keyFields[0];
        this.value1 = field == keyField1 ? value1 : value2;
        field.checkValue(this.value1);

        field = keyFields[1];
        this.value2 = field == keyField2 ? value2 : value1;
        field.checkValue(this.value2);
        hashCode = computeHash();
    }

    public GlobType getGlobType() {
        return type;
    }

    public void apply(Functor functor) throws Exception {
        Field[] fields = type.getKeyFields();
        functor.process(fields[0], value1);
        functor.process(fields[1], value2);
    }

    public void safeApply(Functor functor) {
        try {
            Field[] fields = type.getKeyFields();
            functor.process(fields[0], value1);
            functor.process(fields[1], value2);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return 2;
    }

    protected Object getSwitchValue(Field field) {
        switch (field.getKeyIndex()) {
            case 0:
                return value1;
            case 1:
                return value2;
        }
        throw new InvalidParameter(field + " is not a key field");
    }

    // optimized - do not use generated code
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass().equals(TwoFieldKey.class)) {
            TwoFieldKey twoFieldKey = (TwoFieldKey)o;
            return type == twoFieldKey.getGlobType() &&
                   Utils.equal(twoFieldKey.value1, value1) &&
                   Utils.equal(twoFieldKey.value2, value2);
        }

        if (!Key.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        Key otherKey = (Key)o;
        Field[] fields = type.getKeyFields();
        return type == otherKey.getGlobType()
               && Utils.equal(value1, otherKey.getValue(fields[0]))
               && Utils.equal(value2, otherKey.getValue(fields[1]));
    }

    // optimized - do not use generated code
    public int hashCode() {
        return hashCode;
    }

    private int computeHash() {
        int h = type.hashCode();
        h = 31 * h + (value1 == null ? 31 : value1.hashCode());
        h = 31 * h + (value2 == null ? 31 : value2.hashCode());
        if (h == 0) {
            h = 31;
        }
        return h;
    }

    public FieldValue[] toArray() {
        Field[] fields = type.getKeyFields();
        return new FieldValue[]{
            new FieldValue(fields[0], value1),
            new FieldValue(fields[1], value2),
            };
    }

    public String toString() {
        Field[] fields = type.getKeyFields();
        return getGlobType().getName() + "[" +
               fields[0].getName() + "=" + value1 + ", " +
               fields[1].getName() + "=" + value2 + "]";
    }
}