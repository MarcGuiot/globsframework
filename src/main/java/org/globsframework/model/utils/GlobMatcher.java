package org.globsframework.model.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;

/**
 * @see GlobMatchers
 */
public interface GlobMatcher {
    boolean matches(Glob item, GlobRepository repository);
}
