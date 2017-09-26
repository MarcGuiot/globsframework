package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ElementType.FIELD})
public @interface Required {
   GlobType TYPE = RequiredAnnotationType.TYPE;
}
