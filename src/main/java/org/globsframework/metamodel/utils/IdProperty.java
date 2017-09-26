package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.properties.Property;

public abstract class IdProperty<D, T> implements Property<D, T> {
  private String name;
  private int id;

  public IdProperty(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
