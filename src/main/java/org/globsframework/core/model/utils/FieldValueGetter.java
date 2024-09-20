package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.fields.Field;

public interface FieldValueGetter {
    boolean contains(Field field);

    Object get(Field field);
}
