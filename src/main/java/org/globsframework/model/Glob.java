package org.globsframework.model;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.links.Link;

public interface Glob extends FieldValues {
    GlobType getType();

    Key getKey();

    Key getTargetKey(Link link);

    boolean matches(FieldValues values);

    boolean matches(FieldValue... values);

    FieldValues getValues();

    MutableGlob duplicate();
}
