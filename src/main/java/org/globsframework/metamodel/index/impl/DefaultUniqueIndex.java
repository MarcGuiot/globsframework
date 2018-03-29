package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.index.IndexVisitor;
import org.globsframework.metamodel.index.UniqueIndex;

public class DefaultUniqueIndex extends AbstractSingleIndex implements UniqueIndex {

    public DefaultUniqueIndex(String name) {
        super(name);
    }

    public DefaultUniqueIndex(String name, Field field) {
        super(name);
        setField(field);
    }

    public <T extends IndexVisitor> T visit(T visitor) {
        visitor.visitUniqueIndex(this);
        return visitor;
    }

}
