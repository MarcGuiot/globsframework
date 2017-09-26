package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.ChangeSetListener;
import org.globsframework.model.GlobRepository;

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
