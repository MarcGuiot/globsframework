package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.Key;

import java.util.Set;

public abstract class KeyChangeListener implements ChangeSetListener {

    protected final Key key;

    protected KeyChangeListener(Key key) {
        this.key = key;
    }

    public void globsChanged(ChangeSet changeSet, GlobRepository repository) {
        if (changeSet.containsChanges(key)) {
            update();
        }
    }

    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
        if (changedTypes.contains(key.getGlobType())) {
            update();
        }
    }

    public abstract void update();
}
