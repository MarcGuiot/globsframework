package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface DefaultString {
  String value();
  GlobType TYPE = DefaultStringAnnotationType.DESC;
}