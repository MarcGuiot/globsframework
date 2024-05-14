package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.Annotations;
import org.globsframework.model.Glob;

import java.util.stream.Stream;

public interface MutableAnnotations extends Annotations {

    MutableAnnotations addAnnotation(Glob glob);

    MutableAnnotations addAnnotations(Stream<Glob> globs);
}
