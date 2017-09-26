package org.globsframework.model.format.utils;

import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.GlobListStringifier;
import org.globsframework.model.utils.GlobListMatcher;
import org.globsframework.model.utils.GlobListMatchers;
import org.globsframework.utils.collections.Pair;
import org.globsframework.utils.Strings;

import java.util.ArrayList;
import java.util.List;

public class CompositeGlobListStringifier implements GlobListStringifier {
  private List<Pair<GlobListMatcher, GlobListStringifier>> elements =
    new ArrayList<Pair<GlobListMatcher, GlobListStringifier>>();

  private String separator = " ";

  public CompositeGlobListStringifier() {
  }

  public CompositeGlobListStringifier(String separator) {
    this.separator = separator;
  }

  public void add(GlobListStringifier stringifier) {
    add(GlobListMatchers.ALL, stringifier);
  }

  public void add(GlobListMatcher matcher, GlobListStringifier stringifier) {
    elements.add(new Pair<GlobListMatcher, GlobListStringifier>(matcher, stringifier));
  }

  public String toString(GlobList list, GlobRepository repository) {

    StringBuffer buffer = new StringBuffer();
    boolean empty = true;
    for (Pair<GlobListMatcher, GlobListStringifier> element : elements) {
      GlobListMatcher matcher = element.getFirst();
      if (!matcher.matches(list, repository)) {
        continue;
      }

      GlobListStringifier stringifier = element.getSecond();
      String text = stringifier.toString(list, repository);
      if (Strings.isNullOrEmpty(text)) {
        continue;
      }
      if (!empty) {
        buffer.append(separator);
      }
      buffer.append(text);
      empty = false;
    }

    return buffer.toString();
  }
}
