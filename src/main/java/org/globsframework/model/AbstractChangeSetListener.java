package org.globsframework.model;

import org.globsframework.metamodel.GlobType;

import java.util.Set;

abstract public class AbstractChangeSetListener implements ChangeSetListener {
    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
    }
}
