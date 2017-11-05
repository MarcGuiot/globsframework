package org.globsframework.functional;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

public interface FunctionalKeyBuilder {

    GlobType getType();

    Field[] getFields();

    FunctionalKey create(Glob glob);

    FunctionalKey proxy(Glob glob);

    MutableFunctionalKey create();
}
