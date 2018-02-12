package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.AbstractKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.Key;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class FourFieldKey extends AbstractKey {
    private final GlobType type;
    private final Object value1;
    private final Object value2;
    private final Object value3;
    private final Object value4;
    private final int hashCode;

    public FourFieldKey(Field keyField1, Object value1,
                        Field keyField2, Object value2,
                        Field keyField3, Object value3,
                        Field keyField4, Object value4) throws MissingInfo {
        SingleFieldKey.checkValue(keyField1, value1);
        SingleFieldKey.checkValue(keyField2, value2);
        SingleFieldKey.checkValue(keyField3, value3);
        SingleFieldKey.checkValue(keyField4, value4);
        type = keyField1.getGlobType();

        Field[] keyFields = keyField1.getGlobType().getKeyFields();
        if (keyFields.length != 4) {
            throw new InvalidParameter("Cannot use a three-fields key for type " + keyField1.getGlobType() + " - " +
                                       "key fields=" + Arrays.toString(keyFields));
        }
        Field field;
        field = keyFields[0];
        this.value1 = field == keyField1 ? value1 : field == keyField2 ? value2 : field == keyField3 ? value3 : value4;
        field.checkValue(this.value1);

        field = keyFields[1];
        this.value2 = field == keyField2 ? value2 : field == keyField1 ? value1 : field == keyField3 ? value3 : value4;
        field.checkValue(this.value2);

        field = keyFields[2];
        this.value3 = field == keyField3 ? value3 : field == keyField4 ? value4 : field == keyField2 ? value2 : value1;
        field.checkValue(this.value3);

        field = keyFields[3];
        this.value4 = field == keyField4 ? value4 : field == keyField3 ? value3 : field == keyField2 ? value2 : value1;
        field.checkValue(this.value4);
        hashCode = computeHash();
    }

    public GlobType getGlobType() {
        return type;
    }

    public void apply(Functor functor) throws Exception {
        Field[] fields = type.getFields();
        functor.process(fields[0], value1);
        functor.process(fields[1], value2);
        functor.process(fields[2], value3);
        functor.process(fields[3], value4);
    }

    public void safeApply(Functor functor) {
        try {
            Field[] fields = type.getFields();
            functor.process(fields[0], value1);
            functor.process(fields[1], value2);
            functor.process(fields[2], value3);
            functor.process(fields[3], value4);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return 4;
    }

    protected Object getSwitchValue(Field field) {
        switch (field.getKeyIndex()) {
            case 0:
                return value1;
            case 1:
                return value2;
            case 2:
                return value3;
            case 3:
                return value4;
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
        if (o.getClass() == FourFieldKey.class) {
            FourFieldKey otherSingleFieldKey = (FourFieldKey)o;
            Field[] keyFields = getGlobType().getKeyFields();
            return type == otherSingleFieldKey.getGlobType() &&
                   keyFields[0].valueEqual(otherSingleFieldKey.value1, value1) &&
                   keyFields[1].valueEqual(otherSingleFieldKey.value2, value2) &&
                   keyFields[2].valueEqual(otherSingleFieldKey.value3, value3) &&
                   keyFields[3].valueEqual(otherSingleFieldKey.value4, value4);
        }

        if (!Key.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        Key otherKey = (Key)o;
        Field[] keyFields = type.getKeyFields();
        return type == otherKey.getGlobType()
               && keyFields[0].valueEqual(value1, otherKey.getValue(keyFields[0]))
               && keyFields[1].valueEqual(value2, otherKey.getValue(keyFields[1]))
               && keyFields[2].valueEqual(value3, otherKey.getValue(keyFields[2]))
               && keyFields[3].valueEqual(value4, otherKey.getValue(keyFields[3]));
    }

    // optimized - do not use generated code
    public int hashCode() {
        return hashCode;
    }

    private int computeHash() {
        Field[] fields = type.getKeyFields();
        int h = type.hashCode();
        h = 31 * h + (value1 != null ? fields[0].valueHash(value1) : 0);
        h = 31 * h + (value2 != null ? fields[1].valueHash(value2) : 0);
        h = 31 * h + (value3 != null ? fields[2].valueHash(value3) : 0);
        h = 31 * h + (value4 != null ? fields[3].valueHash(value4) : 0);
        if (h == 0) {
            h = 31;
        }
        return h;
    }

    public FieldValue[] toArray() {
        Field[] fields = type.getFields();
        return new FieldValue[]{
            new FieldValue(fields[0], value1),
            new FieldValue(fields[1], value2),
            new FieldValue(fields[2], value3),
            new FieldValue(fields[3], value4)
        };
    }

    public String toString() {
        Field[] fields = type.getFields();
        return getGlobType().getName() + "[" +
               fields[0].getName() + "=" + value1 + ", " +
               fields[1].getName() + "=" + value2 + ", " +
               fields[2].getName() + "=" + value3 + ", " +
               fields[3].getName() + "=" + value4 + "]";
    }
}