package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.GlobRepository;

import java.util.Set;

public abstract class FieldChangeListener implements ChangeSetListener {

    protected final Field field;

    protected FieldChangeListener(Field key) {
        this.field = key;
    }

    public void globsChanged(ChangeSet changeSet, GlobRepository repository) {
        if (changeSet.containsUpdates(field)) {
            update();
        }
    }

    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
        if (changedTypes.contains(field.getGlobType())) {
            update();
        }
    }

    public abstract void update();
}
