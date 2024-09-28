package org.globsframework.core.metamodel.utils;

import org.globsframework.core.metamodel.Annotations;
import org.globsframework.core.model.Glob;

import java.util.Collection;

public interface MutableAnnotations extends Annotations {

    MutableAnnotations addAnnotation(Glob glob);

    MutableAnnotations addAnnotations(Collection<Glob> glob);

}
