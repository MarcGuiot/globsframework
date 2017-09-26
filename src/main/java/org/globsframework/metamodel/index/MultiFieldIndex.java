package org.globsframework.metamodel.index;

import org.globsframework.metamodel.Field;

public interface MultiFieldIndex {
  String getName();

  Field[] getFields();

  void visit(MultiFieldIndexVisitor multiFieldIndexVisitor);
}
