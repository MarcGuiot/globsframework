package org.globsframework.model.utils;

import org.globsframework.model.ChangeSetListener;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.Key;
import org.globsframework.metamodel.GlobType;

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
