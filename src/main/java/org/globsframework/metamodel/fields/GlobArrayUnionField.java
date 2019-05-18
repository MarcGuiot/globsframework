package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

import java.util.Collection;

public interface GlobArrayUnionField extends Field {
    Collection<GlobType> getTypes();

    GlobType get(String name);

    /*
    Dangerous
    Use with caution
     */
    void __add__(GlobType type);
}
