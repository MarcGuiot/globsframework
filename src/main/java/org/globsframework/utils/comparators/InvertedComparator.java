package org.globsframework.utils.comparators;

import org.globsframework.model.Glob;

import java.util.Comparator;

public class InvertedComparator implements Comparator<Glob> {
  private Comparator<Glob> comparator;

  public InvertedComparator(Comparator<Glob> comparator) {
    this.comparator = comparator;
  }

  public int compare(Glob o1, Glob o2) {
    return comparator.compare(o2, o1);
  }
}
