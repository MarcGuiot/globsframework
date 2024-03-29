package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface MultiLineText {

    String mimeType() default "text/plain";

    int maxSize() default  -1;

    GlobType TYPE = MultiLineTextType.TYPE;
}
