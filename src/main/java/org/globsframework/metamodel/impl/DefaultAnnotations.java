package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultAnnotations<T extends MutableAnnotations> implements MutableAnnotations<T> {
    volatile private Map<Key, Glob> annotations;

   public DefaultAnnotations() {
      annotations = new HashMap<>();
   }

   public DefaultAnnotations(Glob[] annotations) {
      this.annotations = new HashMap<>(annotations.length);
      for (Glob annotation : annotations) {
         addAnnotation(annotation);
      }
   }

   public DefaultAnnotations(Annotations annotations) {
      Collection<Glob> globs = annotations.listAnnotations();
      this.annotations = new HashMap<>(globs.size());
      for (Glob annotation : globs) {
         this.annotations.put(annotation.getKey(), annotation);
      }
   }

   public T addAnnotation(Glob glob) {
       if (glob != null) {
          synchronized (this) {
             Map<Key, Glob> tmp = new HashMap<>(annotations);
             tmp.put(glob.getKey(), glob);
             annotations = tmp;
          }
       }
      return (T)this;
    }

    public boolean hasAnnotation(Key key) {
        return annotations.containsKey(key);
    }

    public Glob getAnnotation(Key key) {
      Glob annotation = annotations.get(key);
      if (annotation == null) {
        throw new ItemNotFound(key == null ? "null" : key.toString());
      }
      return annotation;
    }

    public Glob findAnnotation(Key key) {
        return annotations.get(key);
    }

    public Collection<Glob> listAnnotations() {
        return Collections.unmodifiableCollection(annotations.values());
    }
}
