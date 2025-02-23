package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.Glob;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Function;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD})
public @interface ReName_ {
    String name();

    String value();

    Function<String, Glob> create = (String str) ->
            ReName.create(str, "");

    GlobType TYPE = ReName.TYPE;
}
