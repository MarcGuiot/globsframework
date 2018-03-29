package org.globsframework.metamodel.index;

public class AbstractIndexVisitor implements IndexVisitor {

    public void visitUniqueIndex(UniqueIndex index) {
        notManaged(index);
    }

    public void visitNotUniqueIndex(NotUniqueIndex index) {
        notManaged(index);
    }

    public void visitNotUnique(MultiFieldNotUniqueIndex index) {
        notManaged(index);
    }

    public void visitUnique(MultiFieldUniqueIndex index) {
        notManaged(index);
    }

    private void notManaged(Index index) {
        throw new RuntimeException(index.getName() + " not managed");
    }
}
