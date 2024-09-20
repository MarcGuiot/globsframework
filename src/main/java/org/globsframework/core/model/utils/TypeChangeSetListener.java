package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.utils.Utils;

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
