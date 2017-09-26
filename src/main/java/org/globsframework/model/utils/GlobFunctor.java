package org.globsframework.model.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;

public interface GlobFunctor {
  void run(Glob glob, GlobRepository repository) throws Exception;
}
