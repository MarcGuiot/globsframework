package org.globsframework.model.format.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.GlobStringifier;
import org.globsframework.utils.Utils;

import java.util.Comparator;

public abstract class AbstractGlobStringifier implements GlobStringifier {
  private Comparator<Glob> comparator;

  protected AbstractGlobStringifier() {
  }

  protected AbstractGlobStringifier(Comparator<Glob> comparator) {
    this.comparator = comparator;
  }

  public Comparator<Glob> getComparator(final GlobRepository globRepository) {
    if (comparator != null) {
      return comparator;
    }
    return new Comparator<Glob>() {
      public int compare(Glob glob1, Glob glob2) {
        return Utils.compare(AbstractGlobStringifier.this.toString(glob1, globRepository),
                             AbstractGlobStringifier.this.toString(glob2, globRepository));
      }
    };
  }
}
