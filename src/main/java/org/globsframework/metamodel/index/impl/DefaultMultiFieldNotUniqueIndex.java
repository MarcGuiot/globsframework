package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.index.IndexVisitor;
import org.globsframework.metamodel.index.MultiFieldNotUniqueIndex;

public class DefaultMultiFieldNotUniqueIndex extends AbstractMultiFieldIndex implements MultiFieldNotUniqueIndex {

    public DefaultMultiFieldNotUniqueIndex(String name) {
        super(name);
    }

    public DefaultMultiFieldNotUniqueIndex(String name, Field[] fields) {
        super(name, fields);
    }

    public <T extends IndexVisitor> T visit(T indexVisitor) {
        indexVisitor.visitNotUnique(this);
        return indexVisitor;
    }
}
