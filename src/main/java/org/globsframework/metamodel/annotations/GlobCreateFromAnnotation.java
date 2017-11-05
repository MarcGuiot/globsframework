package org.globsframework.metamodel.annotations;

import org.globsframework.model.Glob;

import java.lang.annotation.Annotation;

public interface GlobCreateFromAnnotation {
    Glob create(Annotation annotation);
}
