package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.index.MultiFieldIndex;

public abstract class AbstractMultiFieldIndex implements MultiFieldIndex {
    private String name;
    private Field[] fields;

    public AbstractMultiFieldIndex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setField(Field[] fields) {
        this.fields = fields;
    }
}
