package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.GlobType;

public interface MutableGlobType extends GlobType {


    <T> void register(Class<T> klass, T t);
}
