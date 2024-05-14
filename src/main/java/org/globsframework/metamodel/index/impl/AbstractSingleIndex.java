package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.index.SingleFieldIndex;

import java.util.stream.Stream;

public abstract class AbstractSingleIndex implements SingleFieldIndex {
    protected String name;
    private Field field;

    public AbstractSingleIndex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Stream<Field> fields() {
        return Stream.of(field);
    }

    public void setField(Field field) {
        this.field = field;
    }
}
