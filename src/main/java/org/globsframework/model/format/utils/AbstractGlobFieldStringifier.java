package org.globsframework.model.format.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.GlobStringifier;
import org.globsframework.model.utils.GlobFieldComparator;

import java.util.Comparator;

public abstract class AbstractGlobFieldStringifier<F extends Field, T> implements GlobStringifier {
  private F field;

  public AbstractGlobFieldStringifier(F field) {
    this.field = field;
  }

  public String toString(Glob glob, GlobRepository globRepository) {
    if (glob == null) {
      return "";
    }
    T value = (T)glob.getValue(field);
    if (value == null) {
      return "";
    }
    return valueToString(value);
  }

  protected abstract String valueToString(T value);

  public Comparator<Glob> getComparator(GlobRepository globRepository) {
    return new GlobFieldComparator(field);
  }
}
