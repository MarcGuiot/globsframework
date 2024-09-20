package org.globsframework.core.model;

import org.globsframework.core.metamodel.GlobType;

import java.util.Set;

public interface ChangeSetListener {
    void globsChanged(ChangeSet changeSet, GlobRepository repository);

    void globsReset(GlobRepository repository, Set<GlobType> changedTypes);
}
