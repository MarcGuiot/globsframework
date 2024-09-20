package org.globsframework.core.model.utils;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;

public interface GlobFunctor {
    void run(Glob glob, GlobRepository repository) throws Exception;
}
