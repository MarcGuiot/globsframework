package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.impl.AbstractFieldValues;
import org.globsframework.utils.exceptions.InvalidParameter;

import java.util.Arrays;

public class GlobArrayFieldValues extends AbstractFieldValues {
    private GlobType type;
    private Object[] values;

    public GlobArrayFieldValues(GlobType type, Object[] values) throws InvalidParameter {
        this.type = type;
        this.values = values;
        if (values.length != type.getFieldCount()) {
            throw new InvalidParameter("Values should have " + type.getFieldCount() + " elements instead of " +
                                       values.length + " for type " + type.getName() +
                                       " - array content: " + Arrays.toString(values));
        }
    }

    protected Object doCheckedGet(Field field) {
        return values[field.getIndex()];
    }

    public boolean contains(Field field) {
        return type.equals(field.getGlobType());
    }

    public int size() {
        return values.length;
    }

    public <T extends FieldValues.Functor> T apply(T functor) throws Exception {
        for (Field field : type.getFields()) {
            functor.process(field, values[field.getIndex()]);
        }
        return functor;
    }

    public FieldValue[] toArray() {
        FieldValue[] result = new FieldValue[values.length];
        int index = 0;
        for (Field field : type.getFields()) {
            result[index++] = new FieldValue(field, values[field.getIndex()]);
        }
        return result;
    }
}
