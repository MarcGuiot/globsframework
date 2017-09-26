package org.globsframework.model.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Glob;
import org.globsframework.model.FieldValue;

public class ReadOnlyGlob extends AbstractGlob {
  public ReadOnlyGlob(GlobType type, FieldValue... values) {
    super(type, values);
  }

  private ReadOnlyGlob(GlobType type, Object[] values) {
    super(type, values);
  }

  public boolean exists() {
    return true;
  }

  public void dispose() {
  }

  public Glob duplicate() {
    return new ReadOnlyGlob(type, duplicateValues());
  }

}
