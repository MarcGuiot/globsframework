package org.globsframework.streams;

import org.globsframework.metamodel.Field;
import org.globsframework.streams.accessors.Accessor;

import java.util.Collection;

public interface DbStream {

    boolean next();

    Collection<Field> getFields();

    Accessor getAccessor(Field field);

    void close();
}
