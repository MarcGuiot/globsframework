package org.globsframework.model.utils;

import org.globsframework.model.ChangeSet;
import org.globsframework.model.GlobRepository;

public interface ChangeSetMatcher {
    boolean matches(ChangeSet changeSet, GlobRepository repository);
}
