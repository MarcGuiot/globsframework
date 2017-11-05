package org.globsframework.model.utils;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.ChangeSetListener;
import org.globsframework.model.GlobRepository;
import org.globsframework.utils.Utils;

import java.util.List;
import java.util.Set;

public abstract class TypeChangeSetListener implements ChangeSetListener {

    private List<GlobType> types;

    protected TypeChangeSetListener(GlobType[] types) {
        this.types = Utils.list(types);
    }

    protected TypeChangeSetListener(GlobType type, GlobType... additionalTypes) {
        this.types = Utils.list(type, additionalTypes);
    }

    public void globsChanged(ChangeSet changeSet, GlobRepository repository) {
        for (GlobType type : types) {
            if (changeSet.containsChanges(type)) {
                update(repository);
                return;
            }
        }
    }

    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
        for (GlobType type : types) {
            if (changedTypes.contains(type)) {
                update(repository);
                return;
            }
        }
    }

    public abstract void update(GlobRepository repository);
}
