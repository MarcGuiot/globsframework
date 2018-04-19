package org.globsframework.streams.accessors;

import org.globsframework.model.Glob;

public interface GlobsAccessor extends Accessor {
    Glob[] getGlobs();
}
