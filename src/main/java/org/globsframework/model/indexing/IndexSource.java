package org.globsframework.model.indexing;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

public interface IndexSource {
    Iterable<Glob> getGlobs(GlobType globType);
}
