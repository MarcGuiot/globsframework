package org.globsframework.model.indexing;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.function.Consumer;

public interface IndexSource {
    void getGlobs(GlobType globType, Consumer<Glob> consume);
}
