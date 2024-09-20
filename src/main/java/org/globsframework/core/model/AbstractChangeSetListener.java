package org.globsframework.core.model;

import org.globsframework.core.metamodel.GlobType;

import java.util.Set;

abstract public class AbstractChangeSetListener implements ChangeSetListener {
    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
    }
}
