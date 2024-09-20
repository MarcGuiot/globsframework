package org.globsframework.core.functional.impl;

import org.globsframework.core.functional.FunctionalKey;
import org.globsframework.core.functional.FunctionalKeyBuilder;
import org.globsframework.core.functional.MutableFunctionalKey;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.FieldValues;

public class OneFunctionalKeyBuilder implements FunctionalKeyBuilder {
    private Field field;
    private Field fields[];

    public OneFunctionalKeyBuilder(Field field) {
        this.field = field;
        fields = new Field[]{field};
    }

    public GlobType getType() {
        return field.getGlobType();
    }

    public Field[] getFields() {
        return fields;
    }

    public FunctionalKey create(FieldValues fieldValues) {
        return new OneFieldMutableKey(this, fieldValues.getValue(field));
    }

    public FunctionalKey proxy(FieldValues fieldValues) {
        return create(fieldValues);
    }

    public MutableFunctionalKey create() {
        return new OneFieldMutableKey(this);
    }

    public String toString() {
        return "OneFunctionalKeyBuilder{" +
                "field=" + field +
                '}';
    }
}
