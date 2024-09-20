package org.globsframework.core.metamodel.index.impl;

import org.globsframework.core.metamodel.index.*;

public class IsUniqueIndexVisitor implements IndexVisitor {
    private boolean isUnique = false;


    public boolean isUnique() {
        return isUnique;
    }

    public void visitUniqueIndex(UniqueIndex index) {
        isUnique = true;
    }

    public void visitNotUniqueIndex(NotUniqueIndex index) {
        isUnique = false;
    }

    public void visitNotUnique(MultiFieldNotUniqueIndex index) {
        isUnique = false;
    }

    public void visitUnique(MultiFieldUniqueIndex index) {
        isUnique = true;
    }
}
