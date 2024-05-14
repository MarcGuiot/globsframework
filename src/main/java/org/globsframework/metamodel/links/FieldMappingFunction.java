package org.globsframework.metamodel.links;

import org.globsframework.metamodel.fields.Field;

public interface FieldMappingFunction {
    void process(Field sourceField, Field targetField);
}
