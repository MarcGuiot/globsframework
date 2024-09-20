package org.globsframework.core.metamodel.index.impl;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.index.IndexVisitor;
import org.globsframework.core.metamodel.index.MultiFieldUniqueIndex;

public class DefaultMultiFieldUniqueIndex extends AbstractMultiFieldIndex implements MultiFieldUniqueIndex {

    public DefaultMultiFieldUniqueIndex(String name) {
        super(name);
    }

    public DefaultMultiFieldUniqueIndex(String name, Field[] fields) {
        super(name, fields);
    }

    public <T extends IndexVisitor> T visit(T indexVisitor) {
        indexVisitor.visitUnique(this);
        return indexVisitor;
    }

}
