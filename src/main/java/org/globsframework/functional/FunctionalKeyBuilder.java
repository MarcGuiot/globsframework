package org.globsframework.functional;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValues;

public interface FunctionalKeyBuilder {

    GlobType getType();

    Field[] getFields();

    FunctionalKey create(FieldValues fieldValues);

    FunctionalKey proxy(FieldValues fieldValues);

    MutableFunctionalKey create();
}
