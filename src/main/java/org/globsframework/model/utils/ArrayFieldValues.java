package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValue;
import org.globsframework.model.impl.AbstractFieldValues;

public class ArrayFieldValues extends AbstractFieldValues {

    private FieldValue[] values;

    public ArrayFieldValues(FieldValue[] values) {
        this.values = values;
    }

    protected Object doCheckedGet(Field field) {
        for (FieldValue value : values) {
            if (value.getField().equals(field)) {
                return value.getValue();
            }
        }
        return null;
    }

    public boolean contains(Field field) {
        for (FieldValue value : values) {
            if (value.getField().equals(field)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return values.length;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        for (FieldValue value : values) {
            value.getField().visit(functor, value.getValue());
        }
        return functor;
    }

    public <T extends Functor> T apply(T functor) throws Exception {
        for (FieldValue value : values) {
            functor.process(value.getField(), value.getValue());
        }
        return functor;
    }

    public FieldValue[] toArray() {
        return values;
    }
}
