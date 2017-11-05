package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.Annotations;
import org.globsframework.model.Glob;

import java.util.stream.Stream;

public interface MutableAnnotations<T extends MutableAnnotations> extends Annotations {

   T addAnnotation(Glob glob);

   T addAnnotations(Stream<Glob> globs);
}
