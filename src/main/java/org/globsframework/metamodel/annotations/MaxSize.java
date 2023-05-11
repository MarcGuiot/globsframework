package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Key;

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

    boolean asText() default false;

    GlobType GLOB_TYPE = MaxSizeType.TYPE;
    Key KEY = MaxSizeType.KEY;

}
