package org.globsframework.metamodel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
@NoType
public @interface Targets {
    Class[] value();
}
