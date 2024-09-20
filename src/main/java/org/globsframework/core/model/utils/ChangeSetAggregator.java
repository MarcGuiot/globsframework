package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.delta.DefaultChangeSet;
import org.globsframework.core.model.delta.MutableChangeSet;

import java.util.Set;

public class ChangeSetAggregator {
    private GlobRepository repository;
    private MutableChangeSet changeSet;
    private Listener listener;

    public ChangeSetAggregator(GlobRepository repository) {
        this(repository, new DefaultChangeSet());
    }

    public ChangeSetAggregator(GlobRepository repository, MutableChangeSet initialChangeSet) {
        this.repository = repository;
        changeSet = initialChangeSet;
        this.listener = new Listener();
        repository.addChangeListener(listener);
    }

    public MutableChangeSet getCurrentChanges() {
        return changeSet;
    }

    public MutableChangeSet dispose() {
        repository.removeChangeListener(listener);
        repository = null;
        try {
            return changeSet;
        } finally {
            changeSet = null;
        }
    }

    public void reset() {
        changeSet = new DefaultChangeSet();
    }

    private class Listener implements ChangeSetListener {
        public void globsChanged(ChangeSet newChanges, GlobRepository globRepository) {
            changeSet.merge(newChanges);
        }

        public void globsReset(GlobRepository globRepository, Set<GlobType> changedTypes) {
            changeSet.clear(changedTypes);
        }
    }
}
