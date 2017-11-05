package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.index.MultiFieldIndexVisitor;
import org.globsframework.metamodel.index.MultiFieldUniqueIndex;

public class DefaultMultiFieldUniqueIndex extends AbstractMultiFieldIndex implements MultiFieldUniqueIndex {

    public DefaultMultiFieldUniqueIndex(String name) {
        super(name);
    }

    public void visit(MultiFieldIndexVisitor multiFieldIndexVisitor) {
        multiFieldIndexVisitor.visitUnique(this);
    }

}
