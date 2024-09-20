package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.GlobRepository;

import java.util.Set;

public abstract class DefaultChangeSetListener implements ChangeSetListener {
    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
    }
}
