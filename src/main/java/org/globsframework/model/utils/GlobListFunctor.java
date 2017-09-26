package org.globsframework.model.utils;

import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;

public interface GlobListFunctor {

  void run(GlobList list, GlobRepository repository);

  static GlobListFunctor NO_OP = new GlobListFunctor() {
    public void run(GlobList list, GlobRepository repository) {
    }
  };

}
