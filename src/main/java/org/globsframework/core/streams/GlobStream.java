package org.globsframework.core.streams;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.streams.accessors.Accessor;

import java.util.Collection;

public interface GlobStream {

    boolean next();

    Collection<Field> getFields();

    Accessor getAccessor(Field field);

    void close();
}
