package org.globsframework.model;

import org.globsframework.metamodel.GlobType;

public interface Key extends FieldValuesAccessor {
    GlobType getGlobType();

    <T extends FieldValues.Functor>
    T applyOnKeyField(T functor) throws Exception;

    <T extends FieldValues.Functor>
    T safeApplyOnKeyField(T functor);

    FieldValues asFieldValues();
}
