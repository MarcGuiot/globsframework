package org.globsframework.model;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;

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
        int c = getGlobType().getName().compareTo(o.getGlobType().getName());
        if (c != 0) {
            return c;
        }
        return compareSameKey(o);
    }

    default int compareSameKey(Key key) {
        return key.getGlobType().sameKeyComparator().compare(this, key);
    }

}
