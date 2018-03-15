package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
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

    public boolean contains(Field field) {
        return field.getGlobType().equals(type);
    }

    public int size() {
        return values.length;
    }

    public <T extends FieldValues.Functor>
    T apply(T functor) throws Exception {
        for (Field field : type.getFields()) {
            functor.process(field, values[field.getIndex()]);
        }
        return functor;
    }

    public FieldValue[] toArray() {
        FieldValue[] array = new FieldValue[values.length];
        int index = 0;
        for (Field field : type.getFields()) {
            array[index] = new FieldValue(field, values[index]);
            index++;
        }
        return array;
    }

    public Key getTargetKey(Link link) {
        if (!link.getSourceType().equals(type)) {
            throw new InvalidParameter("Link '" + link + " cannot be used with " + this);
        }

        KeyBuilder keyBuilder = KeyBuilder.init(link.getTargetType());
        link.apply((sourceField, targetField) -> {
            Object value = getValue(sourceField);
            keyBuilder.set(targetField, value);

        });
        return keyBuilder.get();
    }

    public Object doGet(Field field) {
        return values[field.getIndex()];
    }

    public Object doCheckedGet(Field field) {
        if (!field.getGlobType().equals(type)) {
            throw new ItemNotFound("Field '" + field.getName() + "' is declared for type '" +
                    field.getGlobType().getName() + "' and not for '" + type.getName() + "'");
        }
        return values[field.getIndex()];
    }

    public FieldValues getValues() {
        FieldValuesBuilder builder = FieldValuesBuilder.init();
        for (Field field : type.getFields()) {
            if (!field.isKeyField()) {
                builder.setValue(field, values[field.getIndex()]);
            }
        }
        return builder.get();
    }

    public MutableGlob setObject(Field field, Object value) {
        values[field.getIndex()] = value;
        return this;
    }

    Object[] duplicateValues() {
        Object[] newValues = new Object[values.length];
        System.arraycopy(values, 0, newValues, 0, values.length);
        return newValues;
    }

    public Key getKey() {
        return this;
    }
}
