package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.index.IndexVisitor;
import org.globsframework.metamodel.index.UniqueIndex;

public class DefaultUniqueIndex implements UniqueIndex {
    private Field field;
    private String name;

    public DefaultUniqueIndex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public void visitIndex(IndexVisitor visitor) {
        visitor.visiteUniqueIndex(this);
    }

    public void setField(Field field) {
        this.field = field;
    }
}
