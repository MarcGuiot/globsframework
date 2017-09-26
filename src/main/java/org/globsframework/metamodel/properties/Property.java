package org.globsframework.metamodel.properties;

public interface Property<T, D> {

  String getName();

  int getId();

  D createValue(T value);
}
