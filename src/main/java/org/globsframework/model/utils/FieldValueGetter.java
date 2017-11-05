package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;

public interface FieldValueGetter {
    boolean contains(Field field);

    Object get(Field field);
}
