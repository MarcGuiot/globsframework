package org.globsframework.core.metamodel.index;

public class DispatchIndexVisitor implements IndexVisitor {

    public void visitSingleIndex(SingleFieldIndex index) {
        notManaged(index);
    }

    public void visitMultiFieldIndex(MultiFieldIndex index) {
        notManaged(index);
    }

    public void visitUniqueIndex(UniqueIndex index) {
        visitSingleIndex(index);
    }

    public void visitNotUniqueIndex(NotUniqueIndex index) {
        visitSingleIndex(index);
    }

    public void visitNotUnique(MultiFieldNotUniqueIndex index) {
        visitMultiFieldIndex(index);
    }

    public void visitUnique(MultiFieldUniqueIndex index) {
        visitMultiFieldIndex(index);
    }

    private void notManaged(Index index) {
        throw new RuntimeException(index.getName() + " not managed");
    }
}
