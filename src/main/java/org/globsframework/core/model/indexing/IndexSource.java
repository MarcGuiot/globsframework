package org.globsframework.core.model.indexing;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.Glob;

import java.util.function.Consumer;

public interface IndexSource {
    void getGlobs(GlobType globType, Consumer<Glob> consume);
}
