package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface MultiLineText_ {

    String mimeType() default "text/plain";

    int maxSize() default -1;

    GlobType TYPE = MultiLineText.TYPE;
}
