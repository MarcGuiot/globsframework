package org.globsframework.model.utils;

import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;

public interface GlobListMatcher {
  boolean matches(GlobList list, GlobRepository repository);
}
