package org.globsframework.model;

import org.globsframework.metamodel.GlobType;

import java.util.Set;

public interface ChangeSetListener {
    void globsChanged(ChangeSet changeSet, GlobRepository repository);

    void globsReset(GlobRepository repository, Set<GlobType> changedTypes);
}
