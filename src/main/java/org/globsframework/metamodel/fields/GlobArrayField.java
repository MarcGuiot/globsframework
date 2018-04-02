package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

public interface GlobArrayField extends Field {
    GlobType getType();
}
