package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.index.MultiFieldIndex;

import java.util.stream.Stream;

public abstract class AbstractMultiFieldIndex implements MultiFieldIndex {
    private String name;
    private Field[] fields;

    public AbstractMultiFieldIndex(String name) {
        this.name = name;
    }

    public AbstractMultiFieldIndex(String name, Field[] fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public Stream<Field> fields() {
        return Stream.of(fields);
    }

    public Field[] getFields() {
        return fields;
    }

    public void setField(Field[] fields) {
        this.fields = fields;
    }
}
