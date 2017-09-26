package org.globsframework.model.format;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.utils.EmptyGlobStringifier;

import java.util.Comparator;

public interface GlobStringifier {
  String toString(Glob glob, GlobRepository repository);

  Comparator<Glob> getComparator(GlobRepository repository);
}
