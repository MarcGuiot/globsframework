package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.model.Glob;

import java.lang.annotation.Annotation;

public interface GlobCreateFromAnnotation {
    Glob create(Annotation annotation);
}
