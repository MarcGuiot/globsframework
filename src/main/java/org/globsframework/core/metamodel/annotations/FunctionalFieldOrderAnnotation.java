package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface FunctionalFieldOrderAnnotation {
    int value();

    String name() default "";

    GlobType TYPE = FunctionalFieldOrderType.TYPE;
}
