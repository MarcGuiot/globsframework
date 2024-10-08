package org.globsframework.core.metamodel.index.impl;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.index.IndexVisitor;
import org.globsframework.core.metamodel.index.NotUniqueIndex;

public class DefaultNotUniqueIndex extends AbstractSingleIndex implements NotUniqueIndex {

    public DefaultNotUniqueIndex(String name) {
        super(name);
    }

    public DefaultNotUniqueIndex(String name, Field field) {
        super(name);
        setField(field);
    }

    public <T extends IndexVisitor> T visit(T visitor) {
        visitor.visitNotUniqueIndex(this);
        return visitor;
    }
}
