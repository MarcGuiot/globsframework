package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.Annotations;
import org.globsframework.model.Glob;

import java.util.Collection;

public interface MutableAnnotations<T extends MutableAnnotations> extends Annotations {

   T addAnnotation(Glob glob);

   default T addAnnotations(Collection<Glob> globs){
      globs.forEach(this::addAnnotation);
      return (T)this;
   }
}
