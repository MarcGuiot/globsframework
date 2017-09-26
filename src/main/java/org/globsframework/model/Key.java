package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

public interface Key extends FieldValuesAccessor {
    GlobType getGlobType();

    void applyOnKeyField(FieldValues.Functor functor) throws Exception;

    void safeApplyOnKeyField(FieldValues.Functor functor);

    boolean containsKey(Field field);

    FieldValues asFieldValues();
}
