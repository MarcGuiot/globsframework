package org.globsframework.model.utils;

import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;

import javax.swing.*;

public class GlobListActionAdapter implements GlobListFunctor {
  private Action action;

  public GlobListActionAdapter(Action action) {
    this.action = action;
  }

  public void run(GlobList list, GlobRepository repository) {
    action.actionPerformed(null);
  }
}
