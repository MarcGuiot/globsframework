package org.globsframework.model.format.utils;

import org.globsframework.model.format.GlobStringifier;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;

import java.util.Comparator;

public class EmptyGlobStringifier implements GlobStringifier {

  private Comparator<Glob> comparator;

  public EmptyGlobStringifier() {
    this(new Comparator<Glob>() {
      public int compare(Glob o1, Glob o2) {
        return 0;
      }
    });
  }

  public EmptyGlobStringifier(Comparator<Glob> comparator) {
    this.comparator = comparator;
  }

  public String toString(Glob glob, GlobRepository repository) {
    return "";
  }

  public Comparator<Glob> getComparator(GlobRepository repository) {
    return comparator;
  }
}
