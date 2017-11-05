package org.globsframework.metamodel.index;

public interface IndexVisitor {

    void visiteUniqueIndex(UniqueIndex index);

    void visiteNotUniqueIndex(NotUniqueIndex index);
}
