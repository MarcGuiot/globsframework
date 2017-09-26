package org.globsframework.metamodel.index;

public interface MultiFieldIndexVisitor {
  void visitNotUnique(MultiFieldNotUniqueIndex index);

  void visitUnique(MultiFieldUniqueIndex index);
}
