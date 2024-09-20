package org.globsframework.core.functional;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.FieldValues;

public interface FunctionalKeyBuilder {

    GlobType getType();

    Field[] getFields();

    FunctionalKey create(FieldValues fieldValues);

    FunctionalKey proxy(FieldValues fieldValues);

    MutableFunctionalKey create();
}
