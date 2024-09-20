package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.Key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MaxSize {
    int value();

    boolean allow_truncate() default false;

    String charSet() default "UTF-8";

    GlobType GLOB_TYPE = MaxSizeType.TYPE;

    Key KEY = MaxSizeType.KEY;

}
