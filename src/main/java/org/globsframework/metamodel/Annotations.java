package org.globsframework.metamodel;

import org.globsframework.metamodel.impl.DefaultAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.Ref;

import java.util.Collection;

public interface Annotations {
   Annotations EMPTY = new DefaultAnnotations();

   boolean hasAnnotation(Key key);

   Glob getAnnotation(Key key);

   Glob findAnnotation(Key key);

   Collection<Glob> listAnnotations();

   default <T> T getValueOrDefault(Key key, Field field, T defaultValue) {
      Glob annotation = findAnnotation(key);
      if (annotation != null) {
         return (T)annotation.getValue(field);
      }
      return defaultValue;
   }

   default boolean findAnnotation(Key key, Ref<Glob> result){
      result.set(findAnnotation(key));
      return result.get() != null;
   }

   default <T> boolean findValue(Key key, Field field, Ref<T> result) {
      Glob annotation = findAnnotation(key);
      if (annotation != null) {
         result.set((T)annotation.getValue(field));
         return true;
      }
      return false;
   }
}
