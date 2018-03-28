package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

public abstract class AbstractDefaultGlob extends AbstractMutableGlob {
    protected final GlobType type;
    protected final Object[] values;

    protected AbstractDefaultGlob(GlobType type) {
        this(type, new Object[type.getFieldCount()]);
    }

    public AbstractDefaultGlob(GlobType type, Object[] values) {
        this.type = type;
        this.values = values;
    }

    public GlobType getType() {
        return type;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        for (Field field : type.getFields()) {
            field.visit(functor, values[field.getIndex()]);
        }
        return functor;
    }

    public <T extends FieldValues.Functor>
    T apply(T functor) throws Exception {
        for (Field field : type.getFields()) {
            functor.process(field, values[field.getIndex()]);
        }
        return functor;
    }

    public Object doGet(Field field) {
        return values[field.getIndex()];
    }

    public MutableGlob doSet(Field field, Object value) {
        values[field.getIndex()] = value;
        return this;
    }

    Object[] duplicateValues() {
        Object[] newValues = new Object[values.length];
        System.arraycopy(values, 0, newValues, 0, values.length);
        return newValues;
    }
}
