package org.globsframework.metamodel.properties.impl;

import org.globsframework.metamodel.properties.Property;
import org.globsframework.metamodel.utils.IdProperty;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class PropertiesBuilder<T> {
   private AtomicInteger id = new AtomicInteger(0);

   public interface PropertyBuilder<T, D> {
      D createValue(T value);
   }

   public <D> Property<T, D> createProperty(String name, PropertyBuilder<T, D> function) {
      return new IdProperty<T, D>(name, id.getAndIncrement()) {
         public D createValue(T value) {
            return function.createValue(value);
         }
      };
   }
}
