package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.model.MutableGlob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.BitSet;

public abstract class AbstractDefaultGlob extends AbstractMutableGlob {
    protected final GlobType type;
    protected final BitSet isSet;
    protected final Object[] values;

    protected AbstractDefaultGlob(GlobType type) {
        this(type, new Object[type.getFieldCount()], new BitSet());
    }

    public AbstractDefaultGlob(GlobType type, Object[] values, BitSet isSet) {
        this.type = type;
        this.values = values;
        this.isSet = isSet;
    }

    public GlobType getType() {
        return type;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        for (Field field : type.getFields()) {
            if (isSet(field)) { //  || field.isKeyField()
                field.visit(functor, values[field.getIndex()]);
            }
        }
        return functor;
    }

    public <T extends FieldValues.Functor>
    T apply(T functor) throws Exception {
        for (Field field : type.getFields()) {
            if (isSet(field)) {  //  || field.isKeyField()
                functor.process(field, values[field.getIndex()]);
            }
        }
        return functor;
    }

    public Object doGet(Field field) {
        return values[field.getIndex()];
    }

    public MutableGlob doSet(Field field, Object value) {
        int index = field.getIndex();
        values[index] = value;
        isSet.set(index);
        return this;
    }

    public boolean isSet(Field field) throws ItemNotFound {
        return isSet.get(field.getIndex());
    }

    public MutableGlob unset(Field field) {
        values[field.getIndex()] = null;
        isSet.clear(field.getIndex());
        return this;
    }

    Object[] duplicateValues() {
        Object[] newValues = new Object[values.length];
        System.arraycopy(values, 0, newValues, 0, values.length);
        return newValues;
    }
}
