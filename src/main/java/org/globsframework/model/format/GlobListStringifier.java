package org.globsframework.model.format;

import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;

public interface GlobListStringifier {
    String toString(GlobList list, GlobRepository repository);
}
