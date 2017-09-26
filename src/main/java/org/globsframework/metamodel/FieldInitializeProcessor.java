package org.globsframework.metamodel;

import java.lang.annotation.Annotation;

public interface FieldInitializeProcessor<T> {
   T getValue(GlobType type, Annotations annotations, Annotation[] nativeAnnotations);
}
