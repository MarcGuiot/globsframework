package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKey;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValues;

class TwoFunctionalKeyBuilder implements FunctionalKeyBuilder {
    final Field field1;
    final Field field2;
    private Field fields[];

    public TwoFunctionalKeyBuilder(Field field1, Field field2) {
        this.field1 = field1;
        this.field2 = field2;
        fields = new Field[]{field1, field2};
    }

    public GlobType getType() {
        return field1.getGlobType();
    }

    public Field[] getFields() {
        return fields;
    }

    public FunctionalKey create(FieldValues fieldValues) {
        return new TwoFieldsMutableKey(this,
                fieldValues.getValue(field1),
                fieldValues.getValue(field2));
    }

    public FunctionalKey proxy(FieldValues fieldValues) {
        return create(fieldValues);
    }

    public MutableFunctionalKey create() {
        return new TwoFieldsMutableKey(this);
    }

    public String toString() {
        return "TwoFunctionalKeyBuilder{" +
                "field1=" + field1 +
                ", field2=" + field2 +
                '}';
    }
}
