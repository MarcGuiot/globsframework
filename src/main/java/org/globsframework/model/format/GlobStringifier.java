package org.globsframework.model.format;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;

import java.util.Comparator;

public interface GlobStringifier {
    String toString(Glob glob, GlobRepository repository);

    Comparator<Glob> getComparator(GlobRepository repository);
}
