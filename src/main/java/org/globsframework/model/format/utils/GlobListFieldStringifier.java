package org.globsframework.model.format.utils;

import org.globsframework.model.format.GlobListStringifier;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.metamodel.Field;
import org.globsframework.utils.Strings;

import java.util.Set;

public class GlobListFieldStringifier implements GlobListStringifier {
  private Field field;
  private String textForEmptySelection;
  private String textForMultiSelection;

  public GlobListFieldStringifier(Field field, String textForEmptySelection, String textForMultiSelection) {
    this.field = field;
    this.textForEmptySelection = textForEmptySelection;
    this.textForMultiSelection = textForMultiSelection;
  }

  public String toString(GlobList selected, GlobRepository repository) {
    Set values = selected.getValueSet(field);
    if (values.isEmpty()) {
      return textForEmptySelection;
    }
    if (values.size() == 1) {
      return stringify(values.iterator().next());
    }
    return textForMultiSelection;
  }

  protected String stringify(Object value) {
    return Strings.toString(value);
  }
}