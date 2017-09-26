package org.globsframework.metamodel.properties.impl;

public abstract class InheritedPropertyHolder<T> extends AbstractPropertyHolder<T>{

   public T getValueOwner() {
      return (T)this;
   }
}
