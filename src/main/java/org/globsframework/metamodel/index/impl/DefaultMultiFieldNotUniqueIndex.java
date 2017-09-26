package org.globsframework.metamodel.index.impl;

import org.globsframework.metamodel.index.MultiFieldIndexVisitor;
import org.globsframework.metamodel.index.MultiFieldNotUniqueIndex;

public class DefaultMultiFieldNotUniqueIndex extends AbstractMultiFieldIndex implements MultiFieldNotUniqueIndex {

  public DefaultMultiFieldNotUniqueIndex(String name) {
    super(name);
  }

  public void visit(MultiFieldIndexVisitor multiFieldIndexVisitor) {
    multiFieldIndexVisitor.visitNotUnique(this);
  }
}
