package org.globsframework.metamodel.links;

import org.globsframework.metamodel.Field;

public interface FieldMappingFunction {
  void process(Field sourceField, Field targetField);
}
