package org.globsframework.core.streams.accessors;

import org.globsframework.core.model.Glob;

public interface GlobsAccessor extends Accessor {
    Glob[] getGlobs();
}
