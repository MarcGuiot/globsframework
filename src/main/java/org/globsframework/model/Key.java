package org.globsframework.model;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;

public interface Key extends FieldValuesAccessor {
    GlobType getGlobType();

    <T extends FieldValues.Functor>
    T applyOnKeyField(T functor) throws Exception;

    <T extends FieldValues.Functor>
    T safeApplyOnKeyField(T functor);

    <T extends FieldValueVisitor>
    T acceptOnKeyField(T functor) throws Exception;

    <T extends FieldValueVisitor>
    T safeAcceptOnKeyField(T functor);

    FieldValues asFieldValues();
}
