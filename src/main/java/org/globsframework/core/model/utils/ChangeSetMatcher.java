package org.globsframework.core.model.utils;

import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.GlobRepository;

public interface ChangeSetMatcher {
    boolean matches(ChangeSet changeSet, GlobRepository repository);
}
