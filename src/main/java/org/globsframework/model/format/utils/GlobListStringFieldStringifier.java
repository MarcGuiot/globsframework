package org.globsframework.model.format.utils;

import org.globsframework.model.format.GlobListStringifier;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.metamodel.fields.StringField;

import java.util.Set;

public class GlobListStringFieldStringifier implements GlobListStringifier {
  private StringField field;
  private String textForMultiSelection;

  public GlobListStringFieldStringifier(StringField field, String textForMultiSelection) {
    this.field = field;
    this.textForMultiSelection = textForMultiSelection;
  }

  public String toString(GlobList selected, GlobRepository repository) {
    Set<String> values = selected.getValueSet(field);
    if (values.isEmpty()) {
      return "";
    }
    if (values.size() == 1) {
      return values.iterator().next();
    }
    return textForMultiSelection;
  }
}
