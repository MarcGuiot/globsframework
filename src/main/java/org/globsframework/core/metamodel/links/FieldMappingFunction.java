package org.globsframework.core.metamodel.links;

import org.globsframework.core.metamodel.fields.Field;

public interface FieldMappingFunction {
    void process(Field sourceField, Field targetField);
}
