package org.globsframework.core.model;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.FieldValueVisitor;

public interface Key extends FieldValuesAccessor, Comparable<Key> {
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

    default int compareTo(Key o) {
        GlobType globType = getGlobType();
        GlobType otherGlobType = o.getGlobType();
        if (globType == otherGlobType) {
            return globType.sameKeyComparator().compare(this, o);
        }
        int c = globType.getName().compareTo(otherGlobType.getName());
        if (c != 0) {
            return c;
        }
        throw new RuntimeException("Duplicate GlobType name " + globType.getName());
    }
}
