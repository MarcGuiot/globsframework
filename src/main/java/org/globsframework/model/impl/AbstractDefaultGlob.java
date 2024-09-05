package org.globsframework.model.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.model.MutableGlob;
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
            if (isSet(field)) { //  || field.isKeyField()
                field.accept(functor, values[field.getIndex()]);
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
        setSetAt(index);
        return this;
    }

    public boolean isSet(Field field) throws ItemNotFound {
        final int index = field.getIndex();
        return isSetAt(index);
    }

    abstract public void setSetAt(int index);

    abstract public boolean isSetAt(int index);

    abstract public void clearSetAt(int index);

    public MutableGlob unset(Field field) {
        int index = field.getIndex();
        values[index] = null;
        clearSetAt(index);
        return this;
    }
}
