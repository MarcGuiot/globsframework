package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.impl.AbstractField;

public interface MutableGlobType extends GlobType {
    void addField(AbstractField field);

    void addKey(Field field);

}
