package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface DoublePrecision {
  int value();

   GlobType TYPE = DoublePrecisionAnnotationType.DESC;
}
