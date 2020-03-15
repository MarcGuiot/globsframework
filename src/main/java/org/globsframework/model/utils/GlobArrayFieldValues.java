package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.MutableFieldValues;
import org.globsframework.model.impl.AbstractFieldValues;
import org.globsframework.utils.Unset;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Arrays;

public class GlobArrayFieldValues extends AbstractMutableFieldValues {
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

    public boolean isSet(Field field) throws ItemNotFound {
        return values[field.getIndex()] != Unset.VALUE;
    }

    protected Object doCheckedGet(Field field) {
        Object value = values[field.getIndex()];
        return value == Unset.VALUE ? null : value;
    }

    public boolean contains(Field field) {
        return type.equals(field.getGlobType());
    }

    public int size() {
        return values.length;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        for (Field field : type.getFields()) {
            field.visit(functor, values[field.getIndex()]);
        }
        return functor;
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

    public MutableFieldValues setValue(Field field, Object value) throws InvalidParameter {
        if (field.getGlobType() != type) {
            throw new RuntimeException(field.getFullName() + " is not of type " + type.getName());
        }
        field.checkValue(value);
        values[field.getIndex()] = value;
        return this;
    }
}
