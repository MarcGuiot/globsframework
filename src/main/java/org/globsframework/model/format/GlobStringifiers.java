package org.globsframework.model.format;

import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.utils.GlobTypeUtils;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.LongField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.utils.AbstractGlobFieldStringifier;
import org.globsframework.model.format.utils.AbstractGlobStringifier;
import org.globsframework.model.format.utils.EmptyGlobStringifier;
import org.globsframework.utils.Strings;

import java.util.Comparator;
import java.security.InvalidParameterException;

public class GlobStringifiers {
  public static final GlobStringifier EMPTY = new EmptyGlobStringifier();

  public static GlobStringifier empty(Comparator<Glob> comparator) {
    return new EmptyGlobStringifier(comparator);
  }

  public static GlobStringifier get(final StringField field) {
    return new AbstractGlobFieldStringifier<StringField, String>(field) {
      protected String valueToString(String value) {
        return value;
      }
    };
  }

  public static GlobStringifier get(final IntegerField field) {
    return new AbstractGlobFieldStringifier<IntegerField, Integer>(field) {
      protected String valueToString(Integer value) {
        return value.toString();
      }
    };
  }

  public static GlobStringifier get(final LongField field) {
    return new AbstractGlobFieldStringifier<LongField, Long>(field) {
      protected String valueToString(Long value) {
        return value.toString();
      }
    };
  }

  public static GlobStringifier maxWidth(final GlobStringifier stringifier, final int maxWidth) {
    return new GlobStringifier() {
      public String toString(Glob glob, GlobRepository repository) {
        String string = stringifier.toString(glob, repository);
        return Strings.cut(string, maxWidth);
      }

      public Comparator<Glob> getComparator(GlobRepository repository) {
        return stringifier.getComparator(repository);
      }
    };
  }

  public static GlobStringifier target(final Link link, final GlobStringifier targetStringifier) {
    return new AbstractGlobStringifier() {
      public String toString(Glob glob, GlobRepository repository) {
        if (glob == null) {
          return "";
        }
        Glob target = repository.findLinkTarget(glob, link);
        if (target == null) {
          return "";
        }
        return targetStringifier.toString(target, repository);
      }
    };
  }

  public static GlobStringifier target(final Link link) {
    GlobType targetType = link.getTargetType();
    final StringField targetNamingField = GlobTypeUtils.findNamingField(targetType);
    if (targetNamingField == null) {
      throw new InvalidParameterException("Target type " + targetType.getName() + " has no naming field");
    }
    return new AbstractGlobStringifier() {
      public String toString(Glob glob, GlobRepository repository) {
        if (glob == null) {
          return "";
        }
        Glob target = repository.findLinkTarget(glob, link);
        if (target == null) {
          return "";
        }
        return target.get(targetNamingField);
      }
    };
  }

}
