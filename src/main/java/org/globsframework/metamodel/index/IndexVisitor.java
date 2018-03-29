package org.globsframework.metamodel.index;

public interface IndexVisitor {

    void visitUniqueIndex(UniqueIndex index);

    void visitNotUniqueIndex(NotUniqueIndex index);

    void visitNotUnique(MultiFieldNotUniqueIndex index);

    void visitUnique(MultiFieldUniqueIndex index);

}
