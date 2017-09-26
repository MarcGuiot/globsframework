package org.globsframework.model.indexing;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;

public interface IndexedTable {
  void add(Glob glob);

  void add(Field field, Object newValue, Object oldValue, Glob glob);

  GlobList findByIndex(Object value);

  boolean remove(Field field, Object value, Glob glob);

  boolean remove(Glob glob);

  void removeAll();
}
