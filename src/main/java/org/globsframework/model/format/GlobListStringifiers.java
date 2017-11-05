package org.globsframework.model.format;

import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.utils.GlobListFieldStringifier;
import org.globsframework.model.utils.GlobMatcher;
import org.globsframework.utils.Strings;
import org.omg.CORBA.DoubleHolder;

import java.text.DecimalFormat;
import java.util.Comparator;

public class GlobListStringifiers {

  public static GlobListStringifier fieldValue(Field field, String textForEmptySelection, String textForMultiSelection) {
    return new GlobListFieldStringifier(field, textForEmptySelection, textForMultiSelection);
  }

  public static GlobListStringifier maxWidth(final GlobListStringifier stringifier, final int maxWidth) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        String string = stringifier.toString(list, repository);
        return Strings.cut(string, maxWidth);
      }
    };
  }

  public static GlobListStringifier singularOrPlural(final String emptyText,
                                                     final String singularText,
                                                     final String pluralText) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return emptyText;
        }
        if (list.size() == 1) {
          return singularText;
        }
        return pluralText;
      }
    };
  }

  public static GlobListStringifier valueForEmpty(final String text, final GlobListStringifier stringifier) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return text;
        }
        return stringifier.toString(list, repository);
      }
    };
  }

  public static GlobListStringifier sum(final DoubleField field, final DecimalFormat format, boolean invert) {
    final int multiplier = invert ? -1 : 1;
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        double total = 0;
        for (Glob glob : list) {
          final Double value = glob.get(field);
          if (value != null) {
            total += value * multiplier;
          }
        }
        return format.format(total);
      }
    };
  }

  public static GlobListStringifier sum(final DecimalFormat format, boolean invert, final DoubleField... fields) {
    final int multiplier = invert ? -1 : 1;
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }

        double total = 0;
        for (Glob glob : list) {
          for (DoubleField field : fields) {
            final Double value = glob.get(field);
            if (value != null) {
              total += value * multiplier;
            }
          }
        }
        return format.format(total);
      }
    };
  }

  static class DoubleRef {
    public double value;
  }

  public static GlobListStringifier conditionalSum(final GlobMatcher matcher,
                                                   final DecimalFormat format,
                                                   final DoubleField... fields) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }


        double total = 0;
//        total = list.streamAnnotations().filter(glob -> matcher.matches(glob, repository))
//           .collect(DoubleRef::new, (ref, glob) -> {
//             for (DoubleField field : fields) {
//               ref.value += glob.get(field, 0.);
//             }
//           }, (ref, ref2) -> ref.value += ref2.value).value;

        for (Glob glob : list) {
          if (matcher.matches(glob, repository)) {
            for (DoubleField field : fields) {
                total += glob.get(field, 0.);
            }
          }
        }
        return format.format(total);
      }
    };
  }

  public static GlobListStringifier minimum(final DoubleField field, final DecimalFormat format) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }

        double min = list.get(0).get(field);
        for (Glob glob : list) {
          min = Math.min(min, glob.get(field));
        }
        return format.format(min);
      }
    };
  }

  public static GlobListStringifier maximum(final DoubleField field, final DecimalFormat format) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }

        double max = list.get(0).get(field);
        for (Glob glob : list) {
          max = Math.max(max, glob.get(field));
        }
        return format.format(max);
      }
    };
  }

  public static GlobListStringifier average(final DoubleField field, final DecimalFormat format) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }

        double total = 0;
        for (Glob glob : list) {
          total += glob.get(field);
        }
        return format.format(total / list.size());
      }
    };
  }

  public static GlobListStringifier single(final GlobStringifier stringifier, final String textForMulti) {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        if (list.isEmpty()) {
          return "";
        }
        if (list.size() > 1) {
          return textForMulti;
        }
        return stringifier.toString(list.getFirst(), repository);
      }
    };
  }

  public static GlobListStringifier count() {
    return new GlobListStringifier() {
      public String toString(GlobList list, GlobRepository repository) {
        return Integer.toString(list.size());
      }
    };
  }
}
