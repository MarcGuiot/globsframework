package org.globsframework.core.metamodel.utils;

import org.globsframework.core.metamodel.GlobType;

public interface MutableGlobType extends GlobType {


    <T> void register(Class<T> klass, T t);
}
